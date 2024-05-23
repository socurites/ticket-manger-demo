package com.socurites.coroutineplay

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
    // VM Option 추가 필요: -Dkotlinx.coroutines.debug
    val job1: Job = launch {
        val elapsedTime = measureTimeMillis {
            delay(150)
        }
        println("async task-1 $elapsedTime ms")
    }
    job1.cancel()

    val job2: Job = launch(start = CoroutineStart.LAZY) {
        val elapsedTime = measureTimeMillis {
            delay(100)
        }
        println("async task-2 $elapsedTime ms")
    }

    println("Starting task-2")
    job2.start()
}