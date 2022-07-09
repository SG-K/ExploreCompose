package com.compose.explore.ui.animations.fab.models

import androidx.compose.ui.graphics.vector.ImageVector

data class FabItem(
    val imageResource : ImageVector,
    val title : String,
    val isChild : Boolean = false
)
