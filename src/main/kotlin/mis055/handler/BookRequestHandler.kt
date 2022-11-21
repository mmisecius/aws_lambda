package mis055.handler

import jakarta.inject.Singleton
import mis055.model.Book
import mis055.model.BookSaved
import org.slf4j.LoggerFactory
import java.util.UUID

@Singleton
class BookRequestHandler: BaseHandler<Book, BookSaved>() {

    override fun execute(input: Book): BookSaved {
        val bookSaved = BookSaved()
        bookSaved.name = input.name
        bookSaved.isbn = UUID.randomUUID().toString()
        return bookSaved
    }

    companion object {
        @JvmStatic
        val logger = LoggerFactory.getLogger(BookRequestHandler::class.java)
    }
}