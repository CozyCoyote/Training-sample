package com.cosy.coyote.training.sample

import kotlinx.coroutines.flow.Flow

interface RepoApi {
    suspend fun accounts(): Flow<String>
    suspend fun accounts2(pt: Param): Flow<Contents>
}

data class Contents(
    val cts: String
)

data class Param(
    val p1: String
)
