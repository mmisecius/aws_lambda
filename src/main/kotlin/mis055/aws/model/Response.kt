package mis055.aws.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class Response(
    val msg: String
)