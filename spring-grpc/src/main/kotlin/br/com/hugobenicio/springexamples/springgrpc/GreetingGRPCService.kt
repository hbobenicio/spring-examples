package br.com.hugobenicio.springexamples.springgrpc

import br.com.hugobenicio.springexamples.springgrpc.grpc.Greeting
import org.lognet.springboot.grpc.GRpcService
import br.com.hugobenicio.springexamples.springgrpc.grpc.GreetingService
import br.com.hugobenicio.springexamples.springgrpc.grpc.Subject
import com.google.protobuf.RpcCallback
import com.google.protobuf.RpcController

@GRpcService
class GreetingGRPCService: GreetingService() {

    override fun greet(controller: RpcController?, request: Subject?, done: RpcCallback<Greeting>?) {
        val subjectName = request?.name ?: "World"
        println(subjectName)
        val greeting: Greeting = Greeting.newBuilder()
            .setMessage("Hello $subjectName")
            .build()

        done!!.run(greeting)
    }
}

