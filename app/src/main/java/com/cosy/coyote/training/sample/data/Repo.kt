package com.cosy.coyote.training.sample.data

import com.cosy.coyote.training.sample.Contents
import com.cosy.coyote.training.sample.Param
import com.cosy.coyote.training.sample.RepoApi
import com.sample.internal.lib.SessionScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

@SessionScoped<RepoApi>(api = RepoApi::class)
class Repo: RepoApi {

    override suspend fun accounts(): Flow<String> {
        callbackFlow<String> {
            this.close()
        }
        return flow {
            this.emit("ana are mere")
        }
    }

    override suspend fun accounts2(p1: Param): Flow<Contents> {
        TODO("Not yet implemented")
    }
}
