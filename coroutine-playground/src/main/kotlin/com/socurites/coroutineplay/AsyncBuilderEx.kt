package com.socurites.coroutineplay

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val result1: Deferred<Int> = async {
        println("summing first")
        delay(200)
        val result = sum(1, 3)
        println("finshed first")
        result
    }

    val result2: Deferred<Int> = async {
        println("summing second")
        delay(600)
        val result = sum(3, 5)
        println("finshed second")
        result
    }

    println("result1: ${result1.await()}")
    println("result2: ${result2.await()}")
}
fun sum(a: Int, b: Int) = a + b