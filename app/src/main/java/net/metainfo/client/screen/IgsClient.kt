package net.metainfo.client.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import net.metainfo.client.repository.IgsRepository
import net.metainfo.client.repository.SocketRemoteSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val source = SocketRemoteSource("igs.joyjoy.net", 6969)
    val repository = IgsRepository(source, coroutineScope)
    val viewModel: IgsClientViewModel = IgsClientViewModel(repository)

    val uiState by viewModel.getUiStateAsFlow().collectAsStateWithLifecycle(initialValue = "")

    var remoteMsg by remember { mutableStateOf("Test") }
    var sendMsg by remember { mutableStateOf("Shannara") }

    BoxWithConstraints {
        var max_height = maxHeight - 50.dp
        var maxSize: Dp = if (max_height > maxWidth) maxWidth else max_height

        Column {
            TextField(
                value = uiState ?: "",
                onValueChange = {
                    remoteMsg = it
                    Log.d(TAG, "ClientScreen: log ->${remoteMsg}")
                },
                modifier = Modifier
                    .width(maxSize)
                    .height(maxSize)
            )

            TextField(
                value = sendMsg,
                onValueChange = {
                    sendMsg = it
                }
            )

            Button(onClick = { viewModel.send(sendMsg) }) {
                "send"
            }
        }
    }
}