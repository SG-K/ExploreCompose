package com.compose.explore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.compose.explore.ui.fab.FabMenu
import com.compose.explore.ui.fab.models.FabItems
import com.compose.explore.ui.theme.ExploreComposeTheme
import com.compose.explore.ui.view_animations.RotatingImage

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

        val list = listOf<FabItems>(
            FabItems(Icons.Default.PhotoCameraBack, "Camera"),
            FabItems(Icons.Default.Image, "Gallery"),
            FabItems(Icons.Default.Description, "Files"),
        )

        RotatingImage(modifier = Modifier
            .constrainAs(rotatingImage){
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
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