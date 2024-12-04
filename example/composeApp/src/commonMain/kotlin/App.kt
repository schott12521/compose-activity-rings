
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.Markdown
import com.slapps.compose.activityrings.ActivityRings
import com.slapps.compose.activityrings.Ring
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes
import kotlin.random.Random

private const val DEFAULT_ANIMATION_DURATION = 250f
private const val MAX_ANIMATION_DURATION = 1000f
private const val MIN_ANIMATION_DURATION = 0f
private const val DEFAULT_ANIMATION_DELAY = 0
private const val MAX_ANIMATION_DELAY = 200f
private val ACTIVITY_WIDGET_CODE = """
    ActivityRings(
        gap = 2.dp,
        size = 200.dp,
        lineWidth = 15.dp,
        animationSpec = AnimationSpec<Float>?,
        Ring(progress = 0.2f, color = Color.Black),
    )
""".trimIndent()

@Composable
fun App() {
    val ringColors = remember { generateRandomColorPalette(4) }
    val ringProgress = remember { List(4) { mutableStateOf(Random.nextFloat().coerceIn(0.1f, 1f)) } }
    val animationDuration = remember { mutableStateOf(DEFAULT_ANIMATION_DURATION) }
    val animationDelay = remember { mutableStateOf(DEFAULT_ANIMATION_DELAY) }
    val clipboardManager = LocalClipboardManager.current

    val animationSpec: AnimationSpec<Float> = tween(
        durationMillis = animationDuration.value.toInt(),
        delayMillis = animationDelay.value,
        easing = FastOutSlowInEasing
    )

    val darkColors = darkColorScheme()

    MaterialTheme(
        colorScheme = darkColors
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(darkColors.background)
            .padding(16.dp)) {
            ActivityRings(
                gap = 2.dp,
                size = 200.dp,
                lineWidth = 15.dp,
                animationSpec = animationSpec,
                rings = ringProgress.mapIndexed { index, progress ->
                    Ring(progress = progress.value, color = ringColors[index])
                }.toTypedArray()
            )

            ringProgress.forEachIndexed { index, progress ->
                ProgressSlider(
                    value = progress.value,
                    onValueChange = { progress.value = it },
                    color = ringColors[index]
                )
            }

            MarkdownDisplay(
                code = ACTIVITY_WIDGET_CODE,
                clipboardManager = clipboardManager
            )

            AnimationControlSlider(
                label = "Animation Duration",
                value = animationDuration.value,
                onValueChange = { animationDuration.value = it },
                valueRange = MIN_ANIMATION_DURATION..MAX_ANIMATION_DURATION
            )

            AnimationControlSlider(
                label = "Animation Delay",
                value = animationDelay.value.toFloat(),
                onValueChange = { animationDelay.value = it.toInt() },
                valueRange = 0f..MAX_ANIMATION_DELAY
            )
        }
    }
}

@Composable
fun ProgressSlider(value: Float, onValueChange: (Float) -> Unit, color: Color) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = 0.0001f..1f,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = color,
            inactiveTrackColor = color.copy(alpha = 0.3f)
        )
    )
}

@Composable
fun MarkdownDisplay(code: String, clipboardManager: ClipboardManager) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            color = Color.White,
            text = "Source code:",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val isDarkTheme = isSystemInDarkTheme()
        val highlightsBuilder = remember(isDarkTheme) {
            Highlights.Builder().theme(SyntaxThemes.atom(darkMode = isDarkTheme))
        }

        Markdown(
            content = "```\n$code\n```",
            components = markdownComponents(
                codeBlock = { MarkdownHighlightedCodeBlock(it.content, it.node, highlightsBuilder) },
                codeFence = { MarkdownHighlightedCodeFence(it.content, it.node, highlightsBuilder) },
            ),
            modifier = Modifier.clickable {
                copyToClipboard(code, clipboardManager)
            }
        )
    }
}

@Composable
fun AnimationControlSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            color = Color.White,
            text = "$label: ${value.toInt()} ms",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            steps = 9,
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun copyToClipboard(text: String, clipboardManager: ClipboardManager) {
    clipboardManager.setText(buildAnnotatedString { append(text) })
}

fun generateRandomColor(): Color = Color(
    red = Random.nextFloat(),
    green = Random.nextFloat(),
    blue = Random.nextFloat(),
    alpha = 1.0f
)

fun generateRandomColorPalette(count: Int): List<Color> = List(count) { generateRandomColor() }