package net.metainfo.client.repository

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SocketRemoteSource(val host: String, val port: Int) {
    var reader: DataInputStream? = null
    var writer: PrintWriter? = null

    suspend fun connect(scope: CoroutineScope) {
        Log.d(TAG, "connect start: remotesource")
        scope.launch(Dispatchers.IO) {
            val socket = Socket(host, port)
            Log.d(TAG, "remote source connected?: ${socket.isConnected}")
            //reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val inputStream = socket.getInputStream()
            reader = DataInputStream(inputStream)

//            reader = InputStreamReader(socket.getInputStream())
            writer = PrintWriter(socket.getOutputStream(), true)
        }
        Log.d(TAG, "connect end: remotesource")
    }

}