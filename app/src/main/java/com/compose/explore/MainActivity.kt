package com.compose.explore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.compose.explore.ui.animations.fab.FabMenu
import com.compose.explore.ui.animations.fab.models.FabItem
import com.compose.explore.ui.animations.view_animations.ButtonRefresh
import com.compose.explore.ui.theme.ExploreComposeTheme
import com.compose.explore.ui.animations.view_animations.RotatingImage
import androidx.compose.material.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExploreComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                     modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScree()
//                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExploreComposeTheme {
        Greeting("Android")
    }
}

@Composable
@Preview
fun MainScree(){

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        val (fabmenu, rotatingImage) = createRefs()

//        var isSelected by remember{ mutableStateOf(false) }
//        ClickAnimationSample(modifier = Modifier, isSelected = isSelected){
//            isSelected = !isSelected
//        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonRefresh()
        }

        RotatingImage(modifier = Modifier
            .constrainAs(rotatingImage){
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        val list = listOf<FabItem>(
            FabItem(Icons.Default.PhotoCameraBack, "Camera"),
            FabItem(Icons.Default.Image, "Gallery"),
            FabItem(Icons.Default.Description, "Files"),
        )
        FabMenu(
            modifier = Modifier
                .constrainAs(fabmenu){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            childItems = list,
            fabmenuBackground = Color.Black.copy(alpha = 0.1f)
        ){

        }

    }

}