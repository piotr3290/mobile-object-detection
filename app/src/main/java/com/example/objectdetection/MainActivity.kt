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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.objectdetection.ui.theme.AppTheme
import org.pytorch.LiteModuleLoader
import org.pytorch.Module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

                val moduleName by remember { mutableStateOf("YOLO v5") }
                val module: Module =
                    LiteModuleLoader.loadModuleFromAsset(assets, "yolov5s.torchscript.ptl")
                val navController = rememberNavController()

                Scaffold(
                    topBar = { AppBar(navController = navController) }
                ) { contentPadding ->
                    Navigation(
                        navController = navController,
                        modifier = Modifier.padding(contentPadding),
                        module = module,
                        modelName = moduleName
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