package com.example.objectdetection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController, modelName: String) {
    Scaffold(bottomBar = {
        Text(
            text = modelName,
            color = Color.Black,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }) { contentPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            item {
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large,
                    onClick = { navController.navigate(Screen.ImageScreen.route) },
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.photo_camera_48),
                        contentDescription = "Photo"
                    )
                }
            }

            item {
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        navController.navigate(Screen.CameraScreen.route)
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_front_48),
                        contentDescription = "Camera"
                    )
                }
            }

            item {
                FloatingActionButton(
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        //TODO - implement loading model
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_file_upload_48),
                        contentDescription = "Model"
                    )
                }
            }
        }
    }


}