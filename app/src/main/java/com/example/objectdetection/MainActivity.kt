package com.example.objectdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.objectdetection.ui.theme.AppTheme
import org.pytorch.LiteModuleLoader
import org.pytorch.Module

class Model(name: String, module: Module, classes: List<String>) {
    var name by mutableStateOf(name)
    var module by mutableStateOf(module)
    var classes by mutableStateOf(classes)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val model = remember {
                    Model(
                        "YOLOv5",
                        LiteModuleLoader.loadModuleFromAsset(assets, "yolov5s.torchscript.ptl"),
                        YoloV5Classes.CLASSES
                    )
                }
                Scaffold(
                    topBar = { AppBar(navController = navController) }
                ) { contentPadding ->
                    Navigation(
                        navController = navController,
                        model = model,
                        modifier = Modifier.padding(contentPadding)
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {}
}