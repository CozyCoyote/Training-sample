package com.cosy.coyote.training.data

import android.content.Context
import com.sample.internal.lib.SessionScoped

@SessionScoped<LocalStoreApi>(LocalStoreApi::class)
class LocalStore(): LocalStoreApi {

    override suspend fun saveBackgroundImage(uri: String) {
        //TODO copy to local storage
        //TODO save to preferences
    }

    fun doStuff(): Int {
        return 23
    }

}

interface LocalStoreApi {
    suspend fun saveBackgroundImage(uri: String)
}
