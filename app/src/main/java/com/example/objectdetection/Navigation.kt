package com.example.objectdetection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
    model: Model,
    modifier: Modifier,
    onModelChange: (ModelParams) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, model = model)
        }
        composable(route = Screen.ImageScreen.route) {
            ImageScreen(model = model)
        }
        composable(route = Screen.CameraScreen.route) {
            CameraScreen(model = model)
        }
        composable(route = Screen.ModelScreen.route) {
            ModelScreen(model, onModelChange)
        }
    }
}