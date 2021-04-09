package mis055.aws.model

import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import java.util.*

@Introspected
data class SourceData(
    var uuid: UUID? = null,
    var price: BigDecimal? = null,
    var desc: String? = null,
    var dummy: Long? = null
)
