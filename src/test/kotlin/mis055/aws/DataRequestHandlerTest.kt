package mis055.aws

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.prop
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.util.*
import javax.inject.Singleton
import mis055.aws.configuration.DataEndpointFactory
import mis055.aws.handler.DataRequestHandler
import mis055.aws.model.Invoice
import mis055.aws.model.Response
import mis055.aws.model.SourceData
import org.junit.jupiter.api.Test


@MicronautTest
class DataRequestHandlerTest {

    @Test
    fun testHandler() {

        // given
        val handler = DataRequestHandler()
        val sourceData = SourceData(UUID.randomUUID(), BigDecimal.valueOf(123.45), "this is a description", 234)

        // when
        val result: Response = handler.execute(sourceData)

        assertThat(result).all {
            isNotNull()
            prop(Response::msg).isEqualTo("Done")
        }
    }

    @Test
    fun testHandler_null() {

        // given
        val handler = DataRequestHandler()

        // when
        val result: Response = handler.execute(null)

        assertThat(result).all {
            isNotNull()
            prop(Response::msg).isEqualTo("No data received")
        }
    }

    @Factory
    class MockDataEndpointFactory {
        @Singleton
        @Replaces(value = DynamoDBMapper::class, factory = DataEndpointFactory::class)
        fun mockDynamoDBMapper(): DynamoDBMapper {
            val dbMapper = mockk<DynamoDBMapper>()
            every { dbMapper.save(any<Invoice>()) } returns Unit
            return dbMapper
        }
    }
}