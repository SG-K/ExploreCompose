package com.compose.explore.ui.animations.content_animations

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ConTentType{
    WORK, PARTY
}

fun ConTentType.getOpposite() = run {
    when(this){
        ConTentType.PARTY -> ConTentType.WORK
        ConTentType.WORK -> ConTentType.PARTY
    }
}

fun ConTentType.getDisplayString() = run {
    when(this){
        ConTentType.PARTY -> "Party"
        ConTentType.WORK -> "Work"
    }
}

@Composable
fun CrossFaseLayouts(

){

    var contenttype by remember{ mutableStateOf(ConTentType.WORK) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Crossfade(
            targetState = contenttype,
            animationSpec = tween(durationMillis = 500)
        ) { _contenttype->

            when(_contenttype){
                ConTentType.PARTY -> {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = "Work",
                        modifier = Modifier.size(50.dp)
                    )
                }
                ConTentType.WORK -> {
                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = "Party",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = { contenttype = contenttype.getOpposite() }) {
            Text(text = contenttype.getDisplayString() )
        }
    }

}