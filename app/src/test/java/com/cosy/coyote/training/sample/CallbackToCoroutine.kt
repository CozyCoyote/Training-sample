package com.cosy.coyote.training.sample

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
class CallbackToCoroutine {

    @Test
    fun `simple callback`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            simpleCallback {
                this.trySend("element 2")
                this.close()
            }
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }

    @Test
    fun `delayed callback`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            delayedCallback {
                this.trySend("element 2")
                this.close()
            }
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }

    @Test
    fun `threaded callback`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            threadedCallback {
                this.trySend("element 2")
                this.close()
            }
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }

    @Test
    fun `threaded callback to coroutine`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            threadedCallbackToCoroutine {
                this.trySend("element 2")
                this.close()
            }
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }

    @Test
    fun `threaded coroutine`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            val value = threadedCoroutineValue(2)
            this.trySend(value)
            this.close()
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }

    @Test
    fun `threaded async coroutine`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            val value = threadedAsyncCoroutineValue(2)
            this.trySend(value)
            this.close()
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }

    @Test
    fun `threaded with context coroutine`() = runTest {
        logWithTimestamp("test start")
        val flow = callbackFlow {
            this.send("element 1")
            val value = threadedWithContextCoroutineValue(this.coroutineContext, 2)
            this.trySend(value)
            this.close()
        }

        flow.collect {
            logWithTimestamp(it)
        }
    }


    private fun simpleCallback(callback: () -> Unit) {
        callback()
    }

    private fun delayedCallback(callback: () -> Unit) {
        Thread.sleep(200L)
        callback()
    }

    private fun threadedCallback(callback: () -> Unit) {
        Thread {
            Thread.sleep(200L)
            callback()
        }.start()
    }

    private suspend fun threadedCallbackToCoroutine(callback: () -> Unit) {
        suspendCoroutine {
            Thread {
                Thread.sleep(200L)
                callback()
                it.resume(null)
            }.start()
        }
    }

    private suspend fun threadedCoroutineValue(count: Int): String {
        return suspendCoroutine {
            Thread {
                Thread.sleep(200L)
                it.resume("element $count")
            }.start()
        }
    }

    private suspend fun threadedAsyncCoroutineValue(count: Int): String {
        return coroutineScope {
            val valueDeferred = async {
                Thread.sleep(200L)
                "element $count"
            }
            val value = valueDeferred.await()
            return@coroutineScope value
        }
    }

    private suspend fun threadedWithContextCoroutineValue(context: CoroutineContext, count: Int): String {
        return withContext(context) {
            Thread.sleep(200L)
            return@withContext "element $count"
        }
    }

    private suspend fun simpleCoroutineValue(count: Int): String {
        return suspendCoroutine {
            it.resume("element $count")
        }
    }

}
