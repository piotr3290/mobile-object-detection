package com.example.objectdetection

sealed class Screen(val route: String) {
    object MainScreen : Screen("Object Detection")
    object ImageScreen : Screen("Classify Uploaded Image")
    object CameraScreen : Screen("Classify Camera Video")
    object ModelScreen : Screen("Choose Classification Model")
}
