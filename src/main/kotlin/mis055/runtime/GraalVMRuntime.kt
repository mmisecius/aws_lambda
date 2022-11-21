package mis055.runtime

import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime
import mis055.handler.DefaultHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GraalVMRuntime<T : Any, R : Any> : AbstractMicronautLambdaRuntime<T, R, T, R>() {

    override fun createHandler(vararg args: String): Any {
        return createRequestStreamHandler(*args)
    }

    override fun createRequestStreamHandler(vararg args: String): RequestStreamHandler {

        if (args.isEmpty()) {
            val errMsg = "No handler has been passed into runtime as parameter"
            logger.error(errMsg)
            throw IllegalStateException(errMsg)
        }

        val handlerClassName = args[0]
        logger.info("The following handler '$handlerClassName' has been passed to runtime")

        println("GGGGGGGGGGGGGGGGGGGG")
        val handler = DefaultHandler(handlerClassName)
        println("GGGGGGGGGGGGGGGGGGGG $handler")
        println("GGGGGGGGGGGGGGGGGGGG ${handler.applicationContext}")
        logger.info("DefaultHandler handler has been initialized")
        return handler
    }

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            GraalVMRuntime<Any, Any>().run(*args)
        }

        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(GraalVMRuntime::class.java)
    }
}
