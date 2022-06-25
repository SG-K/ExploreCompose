package com.compose.explore.ui.animations.view_animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RotatingImage(
    modifier: Modifier
){


    val _infiniteTransition = rememberInfiniteTransition()
    val twinCircleAnimation by _infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Icon(
        imageVector = Icons.Default.Settings,
        contentDescription = "Settings",
        tint = Color.Black,
        modifier = modifier
            .size(48.dp,48.dp)
            .rotate(twinCircleAnimation)
    )

}
