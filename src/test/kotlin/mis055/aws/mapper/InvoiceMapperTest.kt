package mis055.aws.mapper

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.prop
import mis055.aws.model.Invoice
import mis055.aws.model.SourceData
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import java.math.BigDecimal
import java.util.*

class InvoiceMapperTest {

    @Test
    fun testConvertToDto() {

        // given
        val converter = Mappers.getMapper(InvoiceMapper::class.java)
        val sourceData = SourceData(UUID.randomUUID(), BigDecimal.valueOf(123.45), "this is a description", 234)

        // when
        val result = converter.map(sourceData)

        // then
        assertThat(result).all {
            isNotNull()
            prop(Invoice::id).isEqualTo(sourceData.uuid.toString())
            prop(Invoice::price).isEqualTo(sourceData.price)
            prop(Invoice::description).isEqualTo(sourceData.desc)
        }
    }

}