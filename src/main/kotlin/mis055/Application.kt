package mis055

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.CommandLinePropertySource
import io.micronaut.core.cli.CommandLine
import io.micronaut.function.aws.MicronautLambdaContext

class Application {

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            val commandLine = CommandLine.parse(*args)
            val context = ApplicationContext.builder().environments(MicronautLambdaContext.ENVIRONMENT_LAMBDA)
                .propertySources(CommandLinePropertySource(commandLine)).start()
        }
    }
}