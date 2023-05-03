package com.example.objectdetection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController, model: Model) {
    val modelNameText = "Model: ${model.name}"
    val modelNameTextWidth = LocalConfiguration.current.screenWidthDp.dp - 80.dp
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    Text(
                        text = modelNameText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(modelNameTextWidth)
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { navController.navigate(Screen.ModelScreen.route) }) {
                        Icon(Icons.Filled.Edit, Screen.ModelScreen.route)
                    }
                }
            )
        },
    ) { contentPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            item {
                LargeFloatingActionButton(
                    onClick = { navController.navigate(Screen.ImageScreen.route) },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.photo_camera_48),
                        contentDescription = Screen.ImageScreen.route
                    )
                }
            }
            item {
                LargeFloatingActionButton(
                    onClick = { navController.navigate(Screen.CameraScreen.route) },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_front_48),
                        contentDescription = Screen.CameraScreen.route
                    )
                }
            }
        }
    }


}