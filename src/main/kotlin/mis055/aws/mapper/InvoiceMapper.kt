package mis055.aws.mapper

import mis055.aws.model.Invoice
import mis055.aws.model.SourceData
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import java.util.*

@Mapper
interface InvoiceMapper {

    @Mappings(
        Mapping(source = "uuid", target = "id"),
        Mapping(source = "desc", target = "description")
    )
    fun map(source: SourceData): Invoice

    @JvmDefault
    fun mapUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

}