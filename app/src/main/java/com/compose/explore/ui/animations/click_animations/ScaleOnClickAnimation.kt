package com.compose.explore.ui.animations.click_animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun ClickAnimationSample(
    modifier: Modifier,
    isSelected : Boolean = false,
    color : Color = if (isSelected) Color.Red else Color.Blue,
    clicked : () -> Unit
) {

    Column(
        modifier = modifier
            .clickable(
                onClick = clicked
            )
            .size(100.dp)
            .scale(scale = scaleOnClick(isSelected).value)
            .background(color)
    ) {

    }

}

@Composable
fun scaleOnClick(
    isSelected : Boolean = false,
) : Animatable<Float, AnimationVector1D>{
    val scale = remember{ Animatable( initialValue = 1f ) }
    LaunchedEffect(key1 = isSelected ){
        launch {
            scale.animateTo(
                targetValue = 0.9f,
                animationSpec = tween(
                    durationMillis = 50
                )
            )

            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 50
                )
            )

        }
    }
    return scale
}