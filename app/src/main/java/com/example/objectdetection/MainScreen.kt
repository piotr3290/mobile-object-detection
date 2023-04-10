package com.example.objectdetection

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    LazyColumn {
        item {
            Button(onClick = {
                navController.navigate(Screen.ImageScreen.route)
            }) {
                Text(text = "IMAGE")
            }
        }

        item {
            Button(onClick = {
                navController.navigate(Screen.CameraScreen.route)
            }) {
                Text(text = "CAMERA")
            }
        }
    }

}