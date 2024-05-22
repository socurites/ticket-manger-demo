package com.socurites.webfluxplay.reactor

import reactor.core.publisher.Mono

fun main() {
    val mono: Mono<String> = Mono.just("Hello Reactive World")
    mono.subscribe(::println)
}