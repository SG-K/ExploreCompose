package com.compose.explore.ui.animations.content_animations

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.explore.ui.theme.GreenButton


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NumberUpdateAnimationView(
){

    var displayNumber by remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {

        CircleButton(
            imageVector = Icons.Default.Remove,
            contentDescription = "Minus",
            clickable = displayNumber != 0
        ){
            if (displayNumber != 0){
                displayNumber--
            }
        }
        
        Spacer(modifier = Modifier.size(8.dp))

        AnimatedContent(
            targetState = displayNumber,
            transitionSpec = {
                if ( targetState > initialState ){
                    ContentTransform(
                        targetContentEnter = slideInVertically { height -> height } + fadeIn(),
                        initialContentExit = slideOutVertically { height -> -height } + fadeOut()
                    )
                } else {
                    ContentTransform(
                        targetContentEnter = slideInVertically { height -> -height } + fadeIn(),
                        initialContentExit = slideOutVertically { height -> height } + fadeOut()
                    )
                }
            }
        ) { _displayNumber ->
            Text(
                text = "${_displayNumber}",
                fontSize = 24.sp,
                fontWeight = FontWeight(500),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        CircleButton(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        ){
            displayNumber++
        }

    }

}

@Composable
fun CircleButton(
    imageVector: ImageVector,
    contentDescription : String = "",
    clickable : Boolean = true,
    click: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(40.dp)
            .background(GreenButton.copy(alpha = 0.1f))
            .clickable(
                enabled = clickable,
                onClick = click
            )
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = GreenButton,
            modifier = Modifier
                .size(20.dp)
        )

    }
}
