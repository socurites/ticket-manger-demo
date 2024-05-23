package com.socurites.coroutineplay

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    // VM Option 추가 필요: -Dkotlinx.coroutines.debug
    launch {
        delay(500)  // Thread.sleep과 달리 thread를 중지하지 않고 실행을 일시 중단함
        println("World")
        println(Thread.currentThread().name)
    }

    println("Hello")
    println(Thread.currentThread().name)
}