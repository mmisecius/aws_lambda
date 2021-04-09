package mis055.aws.processor

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import io.micronaut.core.annotation.Introspected
import javax.inject.Singleton
import mis055.aws.mapper.InvoiceMapper
import mis055.aws.model.SourceData
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Introspected
@Singleton
class DataProcessor(
    val mapper: InvoiceMapper,
    val db: DynamoDBMapper
) {

    private val logger: Logger = LoggerFactory.getLogger(DataProcessor::class.java)

    fun processData(sourceData: SourceData) {
        logger.info("Incoming source data: {}", sourceData)

        val invoice = mapper.map(sourceData)
        logger.debug("Mapped invoice: {}", invoice)

        db.save(invoice)

        logger.info("Invoice persisted successfully")
    }
}
