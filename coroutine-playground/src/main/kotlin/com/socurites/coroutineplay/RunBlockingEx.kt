package com.socurites.coroutineplay

import kotlinx.coroutines.runBlocking

fun main() {
    // VM Option 추가 필요: -Dkotlinx.coroutines.debug
    runBlocking {
        println("Hello")
        println(Thread.currentThread().name)
    }

    println("World")
    println(Thread.currentThread().name)
}