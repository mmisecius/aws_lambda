package mis055.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.fasterxml.jackson.core.PrettyPrinter
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider
import io.micronaut.context.ApplicationContext
import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.http.HttpStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.ParameterizedType
import java.time.LocalDateTime
import java.util.UUID
import kotlin.collections.set

abstract class BaseHandler<T : Any, R : Any>(private val applicationContext: ApplicationContext)  : RequestStreamHandler {


    protected val mapper: ObjectMapper = applicationContext.getBean(ObjectMapper::class.java)
    private val requestType: JavaType = this.mapper.typeFactory.constructType(
        (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
    )

    private val configuration = Configuration
        .builder()
        .mappingProvider(JacksonMappingProvider())
        .jsonProvider(JacksonJsonNodeJsonProvider())
        .build()

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        logger.info("CCCCCCCXXXXX")
        println("CCCCCCCXXXXX")
        val inputStreamCopy = input.clone()
        if (logger.isTraceEnabled) {
            val text = inputStreamCopy.reader().readText()
            logger.trace(text)
            inputStreamCopy.reset()
        }

        val node = parseJson(inputStreamCopy)
        val request = mapRequest(node)
        val response = execute(request)
        mapper.writeValue(output, response)
    }

    private fun InputStream.clone(): ByteArrayInputStream {
        val tmpOutput = ByteArrayOutputStream()
        this.copyTo(tmpOutput)
        return ByteArrayInputStream(tmpOutput.toByteArray())
    }

    private fun parseJson(inputStream: InputStream): JsonNode {
        var jsonNode = mapper.readTree(inputStream)
        return jsonNode
    }

    private fun mapRequest(node: JsonNode): T {
        return kotlin.runCatching {
            val parser = node.traverse(mapper)
            mapper.readValue(parser, requestType) as T
        }.onFailure { e ->
            logger.error("Mapping error.", e)
            throw mappingException(requestId = UUID.randomUUID(), payload = e.message ?: "Mapping issue")
        }.getOrThrow()
    }

    /**
     * Perform the handler execution logic
     */
    abstract fun execute(request: T): R

    private fun mappingException(requestId: UUID, payload: String): IllegalStateException {
        val errorPayload: MutableMap<String, Any> = HashMap()
        errorPayload["message"] = "The request cannot be processed due to mapping issues"
        errorPayload["timestamp"] = LocalDateTime.now().toString()
        errorPayload["requestId"] = requestId
        errorPayload["status"] = HttpStatus.BAD_REQUEST.code
        errorPayload["payload"] = serializeWithoutPrettyPrinter(ErrorCause(payload))
        val message = serializeWithoutPrettyPrinter(errorPayload)
        return IllegalStateException(message)
    }

    private fun serializeWithoutPrettyPrinter(payload: Any): String {
        val prettyWriter: PrettyPrinter? = null
        return mapper.writer(prettyWriter).writeValueAsString(payload)
    }

    private data class ErrorCause(val errorCause: String)

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(BaseHandler::class.java)
    }
}
