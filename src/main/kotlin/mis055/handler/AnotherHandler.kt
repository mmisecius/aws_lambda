package mis055.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import io.micronaut.function.executor.FunctionInitializer
import jakarta.inject.Singleton
import mis055.model.Book
import mis055.model.BookSaved

@Singleton
class AnotherHandler :  FunctionInitializer(), RequestHandler<Book, BookSaved> {
    override fun handleRequest(input: Book, context: Context): BookSaved {
        val response = BookSaved()
        response.isbn = "hello"
        response.name = "hello"
        return response
    }
}