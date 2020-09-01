package br.com.hugobenicio.springexamples.springgrpc

import br.com.hugobenicio.springexamples.springgrpc.grpc.Greeting
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/greeting")
class GreetingController {

    data class SubjectPayload(var subject: String)

    @PostMapping("/greet")
    fun greet(@RequestBody payload: SubjectPayload): String {
        return "Hello, ${payload.subject}!"
    }

    @PostMapping("/grpc/greet")
    fun grpcGreet(): String {
        val greeting = Greeting.newBuilder()
            .setMessage("Hello, GRPC!")
            .build()
        return greeting.toString()
    }
}
