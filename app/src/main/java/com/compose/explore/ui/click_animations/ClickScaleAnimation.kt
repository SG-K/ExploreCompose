package com.compose.explore.ui.click_animations

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun clickScaleAnim(

){

    Card(
        backgroundColor = Color.Green,
        onClick = {
            Log.v("sjdhbvjs","card")
        },
    ) {

        Box(
            Modifier.size(100.dp)
        ) {

        }

    }

}