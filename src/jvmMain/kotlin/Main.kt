import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, state = WindowState(
            size = DpSize(300.dp, 300.dp),
            position = WindowPosition(Alignment.Center)
        )
    ) {
        val scope = rememberCoroutineScope()
        var progress by remember { mutableStateOf(0) }
        scope.launch {
            flow {
                if (progress <= 100){
                    delay(100)
                    emit(2)
                }else{
                    progress = 0
                }
            }.collect{
                progress += it
            }
        }


    }
}
