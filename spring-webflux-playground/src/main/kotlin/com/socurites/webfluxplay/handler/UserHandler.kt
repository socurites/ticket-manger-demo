package com.socurites.webfluxplay.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserHandler {
    fun getUser(req: ServerRequest): Mono<ServerResponse> {
        return users.find {
            req.pathVariable("id").toLong() == it.id
        } ?.let {
            ServerResponse.ok().bodyValue(it)
        } ?: ServerResponse.notFound().build()
    }

    fun getAll(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().bodyValue(users)
    }
}

data class User(
    val id: Long,
    val email: String,
)

val users = listOf(
    User(1, "user1@test.com"),
    User(2, "user2@test.com"),
)