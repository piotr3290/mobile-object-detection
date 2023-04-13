package com.example.objectdetection

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {
    TopAppBar(title = {
        Text(text = "Object detection")
    }, navigationIcon = {
        BackIcon(navController = navController)
    })
}