package com.example.objectdetection

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Int.pxToDp() = with(LocalDensity.current) {
    this@pxToDp.toDp()
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current){
    this@dpToPx.toPx()
}