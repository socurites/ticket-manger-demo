package com.socurites.coroutineplay

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    doSomething()
}

suspend fun doSomething() = coroutineScope {
    launch {
        delay(100)
        println("world")
    }

    launch {
        println("hello")
    }
}