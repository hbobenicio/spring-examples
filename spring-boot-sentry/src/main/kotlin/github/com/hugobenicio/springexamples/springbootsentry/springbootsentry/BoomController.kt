package github.com.hugobenicio.springexamples.springbootsentry.springbootsentry

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException
import java.util.*

@RestController()
@RequestMapping("api")
class BoomController {

    val logger: Logger = LoggerFactory.getLogger(BoomController::class.java)

    @GetMapping("boom")
    fun boom() {
        logger.error("not going well...")
        throw RuntimeException("Boom!")
    }
}
