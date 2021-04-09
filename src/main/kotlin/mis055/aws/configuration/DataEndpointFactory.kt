package mis055.aws.configuration

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton


@Factory
class DataEndpointFactory {

    @Singleton
    fun dynamoDBMapper(): DynamoDBMapper {
        val client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build()
        return DynamoDBMapper(client)
    }
}