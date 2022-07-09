package com.compose.explore.ui.animations.fab

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCameraBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.compose.explore.ui.animations.fab.models.FabItem
import com.compose.explore.ui.animations.fab.models.FabState
import com.compose.explore.ui.animations.fab.models.getAlternativestate
import com.compose.explore.ui.theme.Purple500
import kotlinx.coroutines.launch


@Composable
fun FabMenu(
    modifier: Modifier = Modifier,
    fabmenuBackground : Color = Color.White,
    childItems : List<FabItem>,
    childItemClick : (FabItem) -> Unit
){
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var fabState by remember { mutableStateOf(FabState.COLLAPSE) }
    val rootFabClicked = {
        scope.launch {
            fabState = fabState.getAlternativestate()
            rotation.animateTo(
                targetValue = if (fabState == FabState.EXPAND) {
                    rotation.targetValue + 45f
                } else rotation.targetValue - 45f,
                animationSpec = tween(100, easing = LinearEasing)
            )
        }
    }


    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(if (fabState == FabState.EXPAND) fabmenuBackground else Color.Transparent)
    ) {
        val (fabmenu) = createRefs()

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(20.dp)
                .constrainAs(fabmenu) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {

            LazyColumn(
                horizontalAlignment = Alignment.End
            ){
                itemsIndexed(childItems){ _index, _item->
                    FabButtonChild(
                        fabState = fabState,
                        padding = 8.dp,
                        fabItem = _item
                    ){
                        childItemClick(_item)
                    }
                }
            }

            Spacer( modifier = Modifier.height(8.dp) )

            FabButton(
                padding = 16.dp,
                elevation = 8.dp,
                rotation = rotation.value,
                fabItem = FabItem(Icons.Default.Add,"", false),
                endPadding = 0.dp,
                buttonClick = {rootFabClicked()}
            )
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FabButtonChild(
    fabState : FabState,
    padding : Dp,
    elevation : Dp = 4.dp,
    fabItem: FabItem,
    childClick : () -> Unit
){
    AnimatedVisibility(
        visible = fabState == FabState.EXPAND,
        enter = scaleIn(),
        exit = scaleOut()
    ) {

        Column(
            modifier = Modifier.padding(end = 4.dp)
        ) {

            FabButton(
                rotation = 0f,
                padding = padding,
                elevation = elevation,
                buttonClick = childClick,
                fabItem = fabItem
            )

            Spacer( modifier = Modifier.height(8.dp) )

        }
    }
}

@Composable
fun FabButton(
    rotation : Float,
    elevation : Dp = 4.dp,
    endPadding : Dp = 3.dp,
    padding : Dp,
    fabItem: FabItem,
    buttonClick : () -> Unit
){

    Row (
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = endPadding)
    ){

        if (fabItem.title.isNotEmpty()){
            Card (
                elevation = elevation,
                backgroundColor = Purple500,
            ){
                Text(
                    text = fabItem.title,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
        }

        Card (
            elevation = elevation,
            backgroundColor = Purple500,
            shape = CircleShape,
            modifier = Modifier
                .clickable {
                    buttonClick()
                }
                .rotate(
                    if (!fabItem.isChild) {
                        rotation
                    } else 0f
                )
        ){
            Icon(
                imageVector = fabItem.imageResource,
                contentDescription = fabItem.title,
                tint = Color.White,
                modifier = Modifier
                    .padding(padding)
            )
        }
    }
}

@Composable
@Preview
fun FabMenuDisplay(){

    val list = listOf<FabItem>(
        FabItem(Icons.Default.PhotoCameraBack, "Camera", true),
        FabItem(Icons.Default.Image, "Gallery", true),
        FabItem(Icons.Default.Description, "Files", true),
    )

    FabMenu(
        modifier = Modifier,
        childItems = list,
        fabmenuBackground = Color.Black.copy(alpha = 0.1f)
    ){

    }

}