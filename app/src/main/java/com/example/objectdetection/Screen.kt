package com.example.objectdetection

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ImageScreen : Screen("image_screen")
    object CameraScreen : Screen("camera_screen")
}
