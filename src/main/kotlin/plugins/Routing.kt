package pt.miraje.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import pt.miraje.dto.RequestConsumer
import pt.miraje.dto.toSuccessResponse
import pt.miraje.services.KafkaConsumerService


fun Application.configureRouting() {
    routing {
        post("/consume") {
            val requestConsumer = call.receive<RequestConsumer>()

            val kafkaConsumer = KafkaConsumerService(requestConsumer.configuration.toProperties())
            val response = kafkaConsumer.consumeFromBeginning()

            call.respond(status = HttpStatusCode.OK, message = response.toSuccessResponse())
        }
    }
}