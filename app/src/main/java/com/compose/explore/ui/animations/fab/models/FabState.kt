package com.compose.explore.ui.animations.fab.models

enum class FabState {

    EXPAND, COLLAPSE

}

fun FabState.getAlternativestate() : FabState{
    return if (this == FabState.EXPAND){
        FabState.COLLAPSE
    } else {
        FabState.EXPAND
    }
}