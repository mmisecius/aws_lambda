package mis055.aws.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal

@DynamoDBTable(tableName = "invoice")
@Introspected
data class Invoice(

    @DynamoDBHashKey(attributeName = "id")
    val id: String,

    @DynamoDBAttribute(attributeName = "price")
    val price: BigDecimal,

    @DynamoDBAttribute(attributeName = "description")
    var description: String?,

//    var payload: String
)
