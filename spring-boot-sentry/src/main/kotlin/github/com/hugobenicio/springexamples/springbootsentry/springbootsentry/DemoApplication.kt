package github.com.hugobenicio.springexamples.springbootsentry.springbootsentry

import io.sentry.Sentry
import io.sentry.SentryClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
