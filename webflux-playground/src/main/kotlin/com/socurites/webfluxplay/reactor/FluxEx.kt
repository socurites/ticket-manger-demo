package com.socurites.webfluxplay.reactor

import reactor.core.publisher.Flux

fun main() {
    val iphone = Cellphone("iPhone", 100, Currency.KRW)
    val galaxy = Cellphone("Galaxy", 95, Currency.USD)

    val flux: Flux<Cellphone> = Flux.just(iphone, galaxy)

    flux.subscribe(::println)
}

data class Cellphone(
    val name: String,
    val price: Int,
    val currency: Currency,
)

enum class Currency {
    KRW, USD
}