package com.example.objectdetection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.pytorch.Module

@Composable
fun Navigation(
    navController: NavHostController,
    module: Module,
    modifier: Modifier,
    modelName: String
) {

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, modelName = modelName)
        }

        composable(route = Screen.ImageScreen.route) {
            ImageScreen(module = module)
        }

        composable(route = Screen.CameraScreen.route) {
            CameraScreen()
        }
    }
}