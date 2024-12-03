package com.slapps.compose.activityrings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times

@Composable
fun ActivityRingsWidget(
    progress1: Float,
    color1: Color,
    progress2: Float,
    color2: Color,
    progress3: Float,
    color3: Color,
    gap: Dp, // Gap between the innermost and middle ring
    size: Dp, // Size of the outermost ring
    lineWidth: Dp,

) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outermost ring
        SolidActivityRing(
            progress = progress3,
            lineWidth = lineWidth,
            color = color3,
            size = size
        )

        // Calculate the size for the middle ring
        val middleRingSize = size - 2 * (gap + lineWidth / 2 + lineWidth / 2)
        SolidActivityRing(
            progress = progress2,
            lineWidth = lineWidth,
            color = color2,
            size = middleRingSize
        )

        // Calculate the size for the innermost ring
        val innerRingSize = middleRingSize - 2 * (gap + lineWidth / 2 + lineWidth / 2)
        SolidActivityRing(
            progress = progress1,
            lineWidth = lineWidth,
            color = color1,
            size = innerRingSize
        )
    }
}