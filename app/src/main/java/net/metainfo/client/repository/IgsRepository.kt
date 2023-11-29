package net.metainfo.client.repository

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IgsRepository(val source: SocketRemoteSource, val scope: CoroutineScope) {

    val _scope: CoroutineScope = scope

    val receiveMsg: Flow<String?> = flow {
        val msg = source.reader?.readUTF()
        Log.d(TAG, "msg: ${msg}")
        emit(msg)
    }.flowOn(Dispatchers.IO)

    suspend fun connect() {
        Log.d(TAG, "connect start: repository")
        source.connect(scope)
        Log.d(TAG, "connect end: repository")
    }

    fun send(msg: String) {
        source.writer?.print(msg)
    }

}
