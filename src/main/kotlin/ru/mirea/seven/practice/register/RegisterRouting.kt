package ru.mirea.seven.practice.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.seven.practice.cache.InMemoryCache
import ru.mirea.seven.practice.cache.TokenCache
import ru.mirea.seven.practice.utils.isValidEmail
import java.util.*

fun Application.configureRegisterRouting() {
    routing {
        post(path = "/register") {
            val receive = call.receive<RegisterReceiveRemote>()
            if (!isValidEmail(receive.email)) {
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            }

            if (InMemoryCache.userList.map { it.login }.contains(receive.login)) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(login = receive.login, token = token))


            call.respond(RegisterResponseRemote(token = token))
        }
    }
}