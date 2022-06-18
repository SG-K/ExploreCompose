package com.compose.explore.ui.fab

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
import com.compose.explore.ui.fab.models.FabItems
import com.compose.explore.ui.fab.models.FabState
import com.compose.explore.ui.fab.models.getAlternativestate
import com.compose.explore.ui.theme.Purple500
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FabMenu(
    modifier: Modifier,
    fabmenuBackground : Color = Color.Transparent,
    childItems : List<FabItems>,
    childItemClick : (FabItems) -> Unit
){

    var fabState by remember { mutableStateOf(FabState.COLLAPSE) }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
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
            .clickable(enabled = fabState == FabState.EXPAND) {  rootFabClicked() }
    ) {

        val (fabmenu) = createRefs()

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(20.dp)
                .constrainAs(fabmenu){
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
                        rotation = rotation.value,
                        fabItems = _item,
                        showText = true,
                        updateRorate = {
                            childItemClick(_item)
                        }
                    )
                }
            }

            Spacer( modifier = Modifier.height(8.dp) )

            FabButton(
                padding = 16.dp,
                elevation = 8.dp,
                rotation = rotation.value,
                fabItems = FabItems(Icons.Default.Add,""),
                endPadding = 0.dp,
                updateRorate = {rootFabClicked()}
            )
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FabButtonChild(
    fabState : FabState,
    rotation : Float,
    padding : Dp,
    elevation : Dp = 4.dp,
    fabItems: FabItems,
    showText : Boolean = false,
    updateRorate : () -> Unit
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
                rotation = rotation,
                padding = padding,
                elevation = elevation,
                updateRorate = updateRorate,
                fabItems = fabItems,
                showText = showText,
                isChild = true
            )

            Spacer( modifier = Modifier.height(8.dp) )
        }
    }
}

@Composable
fun FabButton(
    rotation : Float,
    padding : Dp,
    elevation : Dp = 4.dp,
    showText : Boolean = false,
    isChild : Boolean = false,
    fabItems: FabItems,
    endPadding : Dp = 3.dp,
    updateRorate : () -> Unit
){

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = endPadding)
    ) {

        AnimatedVisibility(visible = showText && fabItems.title.isNotEmpty()) {
            Card (
                elevation = elevation,
                backgroundColor = Purple500,
            ){
                Text(
                    text = fabItems.title,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))


        Card (
            elevation = elevation,
            backgroundColor = Purple500,
            shape = CircleShape,
            modifier = Modifier
                .clickable {
                    updateRorate()
                }
                .rotate(if (!isChild){rotation} else 0f)
        ){
            Icon(
                imageVector = fabItems.imageResourceId,
                contentDescription = "Fab Button",
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

    val list = listOf<FabItems>(
        FabItems(Icons.Default.PhotoCameraBack, "Camera"),
        FabItems(Icons.Default.Image, "Gallery"),
        FabItems(Icons.Default.Description, "Files"),
    )

    FabMenu(
        modifier = Modifier,
        childItems = list,
        fabmenuBackground = Color.Black.copy(alpha = 0.1f)
    ){

    }

}