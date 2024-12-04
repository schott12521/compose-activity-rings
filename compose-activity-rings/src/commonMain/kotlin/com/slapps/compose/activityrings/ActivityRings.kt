package com.slapps.compose.activityrings

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times

@Composable
fun ActivityRings(
    gap: Dp,
    size: Dp,
    lineWidth: Dp,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 350, easing = FastOutSlowInEasing),
    vararg rings: Ring
) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        var currentSize = size
        for (ring in rings) {
            ActivityRing(
                progress = ring.progress,
                lineWidth = lineWidth,
                color = ring.color,
                size = currentSize,
                animationSpec
            )
            // Decrease the size for the next (inner) ring
            currentSize -= 2 * (gap + lineWidth)
        }
    }
}