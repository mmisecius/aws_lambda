package mis055.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import io.micronaut.function.executor.FunctionInitializer
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.OutputStream

class DefaultHandler(private val handlerClassName: String) : FunctionInitializer(), RequestStreamHandler {

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        println("XXXXXXXXXXXXXXXXXXXx")
        val handlers = applicationContext.getBeansOfType(BaseHandler::class.java)
        println("HHHHHH $handlers")

        try {
            val a = Class.forName(handlerClassName)
            println("AAAAAAA $a")
        } catch (e: Exception) {
            println("EEEEEE $e")
        }

        val handlerInstance = handlers.firstOrNull { it.javaClass.name == handlerClassName }
            ?: throw java.lang.IllegalStateException("$handlerClassName not found in application context")

        handlerInstance.handleRequest(input, output, context)
    }

    companion object {
        @JvmStatic
        val logger = LoggerFactory.getLogger(DefaultHandler::class.java)
    }
}