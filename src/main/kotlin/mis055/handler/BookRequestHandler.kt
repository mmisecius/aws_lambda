package mis055.handler

import io.micronaut.context.ApplicationContext
import jakarta.inject.Singleton
import mis055.model.Book
import mis055.model.BookSaved
import org.slf4j.LoggerFactory
import java.util.UUID

@Singleton
class BookRequestHandler(applicationContext: ApplicationContext): BaseHandler<Book, BookSaved>(applicationContext) {

    override fun execute(input: Book): BookSaved {
        logger.info("Entering handler execute method")
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