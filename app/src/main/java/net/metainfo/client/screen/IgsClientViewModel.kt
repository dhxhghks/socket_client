package net.metainfo.client.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.metainfo.client.repository.IgsRepository

class IgsClientViewModel(private val repository: IgsRepository) : ViewModel() {
    //val igsSource = SocketRemoteSource("igs.joyjoy.net", 6969)

    init {
        repository._scope.launch {
            repository.connect()
            Log.d(TAG, "connected? ")
        }
    }

    fun getUiStateAsFlow(): Flow<String?> {
        return repository.receiveMsg
    }

    fun send(msg: String) {
        Log.d(TAG, "send: ${msg}")
        repository.send(msg)
    }

}

