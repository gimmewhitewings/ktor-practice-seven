package ru.mirea.seven.practice.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(Test("Hello, World!"))
        }
    }
}

@Serializable
data class Test(
    val text: String
)
