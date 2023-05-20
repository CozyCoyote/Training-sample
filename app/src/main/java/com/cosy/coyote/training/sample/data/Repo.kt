package com.cosy.coyote.training.sample.data

import com.sample.internal.lib.SessionScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

@SessionScoped
class Repo {

    suspend fun accounts(): Flow<String> {
        callbackFlow<String> {
            this.close()
        }
        return flow {
            this.emit("ana are mere")
        }
    }

}
