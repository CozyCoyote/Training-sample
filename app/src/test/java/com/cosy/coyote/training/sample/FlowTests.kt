package com.cosy.coyote.training.sample

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
class FlowTests {

    private val callbackFlow = callbackFlow<String> {
        println("creating callback " + System.currentTimeMillis())
        this.send("element 1")
        doCallback {
            this.trySend("element 2")
            this.close()
        }
    }

    private val regularFlow = flow<String> {
        println("creating callback " + System.currentTimeMillis())
        this.emit("element 1")
        Thread.sleep(200L)
    }

    private val channelFlow = channelFlow<String> {
        println("creating callback " + System.currentTimeMillis())
        this.send("element 1")
        this.send("element 2")
        doCallback2 {
            this.trySend("element 3")
            this.close()
        }
//        Thread.sleep(200L)
    }.buffer(capacity = 1)

    @Test
    fun test2() = runTest {
        System.currentTimeMillis()
        println("test start" + System.currentTimeMillis())
//        regularFlow.collect {
//            assertEquals("element 1", it)
//        }
//        regularFlow.collect {
//            assertEquals("element 1", it)
//        }
//        flow.collect()

//        channelFlow.buffer()

//        channelFlow.flowOn(coroutineContext).collect()

        channelFlow.collect {
            println(it)
//            assertEquals("element 1", it)
        }
        channelFlow.collect {
            println(it)
//            assertEquals("element 1", it)
        }

//        regularFlow.buffer(capacity = 1)
//        channelFlow<String> { }.buffer(capacity = 1)
    }

    @Test
    fun test3() {
        runTest {
            channelFlow.collect {
                println(it)
//            assertEquals("element 1", it)
            }
        }
        runTest {
            channelFlow.collect {
                println(it)
//            assertEquals("element 1", it)
            }
        }
    }


    fun doCallback(callback: () -> Unit) {
        Thread {
//            Thread.sleep(10L)
            callback()
        }.start()
    }

    suspend fun doCallback2(callback: () -> Unit) {
        suspendCoroutine<Nothing?> {
            Thread {
                Thread.sleep(120L)
                it.resume(null)
                callback()
            }.start()
        }
//        coroutineScope {
//            this.coroutineContext.
//        }
//        Thread {
//            Thread.sleep(120L)
//            callback()
//        }.start()
    }


}
