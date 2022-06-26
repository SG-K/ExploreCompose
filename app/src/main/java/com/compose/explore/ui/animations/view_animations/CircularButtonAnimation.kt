package com.compose.explore.ui.animations.view_animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.explore.ui.theme.GreenButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ButtonRefresh(isSelected : Boolean = false,clicked : () -> Unit){

    var buttonState by remember { mutableStateOf(ButtonState.IDDLE)}
    val (scaleText, scaleIcon, displayString) = launchEffectAnimation(buttonState, isSelected)

    Column() {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(GreenButton.copy(alpha = 0.1f))
                .height(40.dp)
            .clickableNoRipple {
                clicked()
                buttonState = buttonState.getOppositeState()
            }
        ) {

            Box(
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    text = displayString,
                    color = GreenButton,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .scale(scaleText.value)
                )

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .scale(scaleIcon.value)
                        .rotate(if (buttonState == ButtonState.IDDLE) 0f else rotateComposable()),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = GreenButton,
                    )
                }

            }

        }

        Button(
            onClick = {
                clicked()
                buttonState = buttonState.getOppositeState()
                      },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text ="Click here to change")
        }

    }


}

@Composable
fun launchEffectAnimation(buttonState:ButtonState, isSelected: Boolean) : Triple<Animatable<Float, AnimationVector1D>, Animatable<Float, AnimationVector1D>, String> {
    val scaleText = remember{ Animatable(initialValue = 1f) }
    val scaleIcon = remember{ Animatable(initialValue = 0f) }
    var displayString by remember { mutableStateOf("Refresh")}

    LaunchedEffect(key1 = isSelected ){
        launch {
            if (buttonState == ButtonState.REFRESHING){
                scaleText.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
                displayString = ""
                scaleIcon.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
            } else if (buttonState == ButtonState.IDDLE){

                scaleIcon.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
                displayString = "Refresh"
                scaleText.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
            }
        }
    }

    return Triple(scaleText, scaleIcon, displayString)
}

@Composable
fun rotateComposable() : Float{
    val _infiniteTransition = rememberInfiniteTransition()
    val twinCircleAnimation by _infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    return twinCircleAnimation
}

enum class ButtonState{
    IDDLE, REFRESHING
}

fun ButtonState.getOppositeState() : ButtonState = when(this){
    ButtonState.IDDLE -> ButtonState.REFRESHING
    ButtonState.REFRESHING -> ButtonState.IDDLE
}

inline fun Modifier.clickableNoRipple( crossinline onClick : () -> Unit ) : Modifier= composed {
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = {
                onClick()
            }
        )
    }
}