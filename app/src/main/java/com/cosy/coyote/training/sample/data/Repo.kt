package com.cosy.coyote.training.sample.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class Repo {

    suspend fun accounts(): Flow<String> {
        callbackFlow<String> {
            this.close()
        }
        return flow {
            this.emit("ana are mere")
//            this.emit()
        }
    }

}
