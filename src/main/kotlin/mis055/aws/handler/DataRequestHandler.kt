package mis055.aws.handler

import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import mis055.aws.model.Response
import mis055.aws.model.SourceData
import mis055.aws.processor.DataProcessor
import javax.inject.Inject


@Introspected
class DataRequestHandler : MicronautRequestHandler<SourceData?, Response>() {

    @Inject
    lateinit var dataProcessor: DataProcessor

    override fun execute(input: SourceData?): Response {
        return when {
            input != null -> {
                dataProcessor.processData(input)
                Response("Done")
            }
            else -> Response("No data received")
        }
    }
}