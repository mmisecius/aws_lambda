package mis055
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BookRequestHandlerTest {

    @Test
    fun testHandler() {
        logger.info("AAAAAAA")
    }

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(BookRequestHandlerTest::class.java)
    }
}