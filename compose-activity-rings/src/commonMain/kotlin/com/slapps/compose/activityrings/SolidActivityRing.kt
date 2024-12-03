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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

@Composable
fun SolidActivityRing(
    progress: Float,
    lineWidth: Dp,
    color: Color,
    size: Dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    Canvas(
        modifier = Modifier
            .size(size)
            .padding(lineWidth / 2)
    ) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = (this.size.minDimension - lineWidth.toPx()) / 2f

        // Background circle
        drawCircle(
            color = color.copy(alpha = 0.1f),
            center = center,
            radius = radius,
            style = Stroke(width = lineWidth.toPx())
        )

        // Foreground arc representing progress
        val sweepAngle = animatedProgress * 360f
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(
                (this.size.width - radius * 2) / 2f,
                (this.size.height - radius * 2) / 2f
            ),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = lineWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}