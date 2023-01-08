package com.andymur.pg.java.koroutines

import kotlinx.coroutines.*
import java.time.LocalDateTime


suspend fun waiter(timeout: Long, greetings: String) {
    delay(timeout)
    val timestamp = LocalDateTime.now()
    println("Greetings $greetings at $timestamp")
}

suspend fun returningWaiter(timeout: Long, greetings: String): Long {
    delay(timeout)
    val timestamp = LocalDateTime.now()
    println("Greetings $greetings at $timestamp")
    return System.currentTimeMillis()
}

suspend fun compositeWaiter() : Pair<Long, Long> = coroutineScope {
    val t1 = withContext(Dispatchers.Default) { returningWaiter(1000, "First") }
    val t2 = withContext(Dispatchers.Default) { returningWaiter(1000, "Second") }

    Pair(t1, t2)
}

suspend fun compositeWaiterSimultaneous() : Pair<Long, Long> = coroutineScope {
    val t1 = async { returningWaiter(1000, "First") }
    val t2 = async { returningWaiter(1000, "Second") }

    awaitAll(t1, t2)
    Pair(t1.await(), t2.await())
}

fun compositeRunner(simultaneous: Boolean = true) = runBlocking {
    val result: Pair<Long, Long>
    if (simultaneous) {
        result = compositeWaiterSimultaneous()
    } else {
        result = compositeWaiter()
    }
    println("composite result: ${result.first} ${result.second} and diff: ${result.first - result.second}")
}

fun asyncRunner() = runBlocking {
    launch { waiter(1000, "First") }
    launch { waiter(2000, "Second") }
}

fun syncRunner() = runBlocking {
    waiter(1000, "First")
    waiter(2000, "Second")
}

fun runnerWrapper(name: String = "wrapper", runner: () -> Unit) {
    println("Begin $name")
    runner()
    println("End $name")
}

fun main() {
    runnerWrapper(name = "async", runner = ::asyncRunner)
    runnerWrapper(name = "sync", runner = ::syncRunner)
    runnerWrapper(name = "composite simultaneous", runner = { compositeRunner(true) })
    runnerWrapper(name = "composite non simultaneous", runner = { compositeRunner(false) })
}