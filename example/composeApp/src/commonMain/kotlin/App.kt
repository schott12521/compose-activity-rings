


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.slapps.compose.activityrings.ActivityRingsWidget


@Composable
fun App() {
    val ringProgress = listOf(
        remember { mutableStateOf(0.2f) },
        remember { mutableStateOf(0.6f) },
        remember { mutableStateOf(0.8f) }
    )

    Column {
        ActivityRingsWidget(
            progress1 = ringProgress[0].value,
            color1 = Color.Red,
            progress2 = ringProgress[1].value,
            color2 = Color.Green,
            progress3 = ringProgress[2].value,
            color3 = Color.Blue,
            gap = 2.dp,
            size = 150.dp,
            lineWidth = 15.dp,
        )

        for (progressSlider in ringProgress) {
            Slider(
                value = progressSlider.value,
                onValueChange = { progressSlider.value = it },
                valueRange = 0.0001f..1f, // Range of the slider
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}