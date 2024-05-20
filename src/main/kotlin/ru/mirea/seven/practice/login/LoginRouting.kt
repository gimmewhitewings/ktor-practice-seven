package ru.mirea.seven.practice.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mirea.seven.practice.cache.InMemoryCache
import ru.mirea.seven.practice.cache.TokenCache
import java.util.UUID

fun Application.configureLoginRouting() {
    routing {
        post(path = "/login") {
            val receive = call.receive<LoginReceiveRemote>()
            val first = InMemoryCache.userList.firstOrNull { it.login == receive.login }
            if (first == null) {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {
                if (first.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    cacheToken(receive.login, token)
                    call.respond(LoginResponseRemote(token = token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }
        }
    }
}

private fun cacheToken(login: String, token: String) {
    InMemoryCache.token.add(TokenCache(login = login, token = token))
}
