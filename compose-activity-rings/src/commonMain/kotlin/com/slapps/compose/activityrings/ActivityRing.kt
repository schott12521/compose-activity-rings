package com.slapps.compose.activityrings

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ActivityRing(
    progress: Float,
    lineWidth: Dp,
    gradientColors: List<Color>
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    Canvas(
        modifier = Modifier
            .size(100.dp) // Adjust the size as needed
            .padding(lineWidth / 2)
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = (size.minDimension - lineWidth.toPx()) / 2f

        // Background circle
        drawCircle(
            color = gradientColors.last().copy(alpha = 0.1f),
            center = center,
            radius = radius,
            style = Stroke(width = lineWidth.toPx())
        )

        // Main ring with gradient
        val sweepAngle = animatedProgress * 360f
        drawArc(
            brush = Brush.sweepGradient(
                colors = gradientColors,
                center = center,
            ),
            startAngle = 0f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(
                (size.width - radius * 2) / 2f,
                (size.height - radius * 2) / 2f
            ),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = lineWidth.toPx(), cap = StrokeCap.Round)
        )

        // End cap (rounded butt)
        if (animatedProgress > 0.9f) {
            val angleInRadians = (sweepAngle) * PI / 180.0
            val endX = center.x + radius * cos(angleInRadians).toFloat()
            val endY = center.y + radius * sin(angleInRadians).toFloat()
            val endCapColor = calculateEndButtColor(gradientColors, animatedProgress)

            // Draw end cap circle
            drawCircle(
                color = endCapColor,
                center = Offset(endX, endY),
                radius = lineWidth.toPx() / 2f,
                style = Fill
            )
        }
    }
}

private fun calculateEndButtColor(gradientColors: List<Color>, progress: Float): Color {
    return lerp(gradientColors.first(), gradientColors.last(), progress * 2f)
}

// Extension function to convert Float to Dp
@Composable
fun Float.toDp(): Dp = with(LocalDensity.current) { this@toDp.toDp() }