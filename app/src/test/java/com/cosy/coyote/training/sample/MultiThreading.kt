package com.cosy.coyote.training.sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class MultiThreading {

    @Test
    fun `in order execution`() = runTest {
        val a1 = action1(Dispatchers.IO)
        val a2 = action1(Dispatchers.IO)

        logWithTimestamp("result : " + a1 + a2)
    }

    @Test
    fun `parallel execution`() = runTest {
        val a1Differed = async { action1(Dispatchers.IO) }
        val a2Differed = async { action1(Dispatchers.IO) }

        logWithTimestamp("result : " + a1Differed.await() + a2Differed.await())
    }

    @Test
    fun `same thread execution`() = runTest {
        val a1Differed = async { action1(coroutineContext) }
        val a2Differed = async { action1(coroutineContext) }

        logWithTimestamp("result : " + a1Differed.await() + a2Differed.await())
    }


    suspend fun action1(context: CoroutineContext): Int = withContext(context) {
        logWithTimestamp("start action")
        Thread.sleep(200L)
        23
    }

}
