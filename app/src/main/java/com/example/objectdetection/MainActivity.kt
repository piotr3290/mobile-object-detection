package com.example.objectdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.objectdetection.ui.theme.ObjectDetectionTheme
import org.pytorch.LiteModuleLoader
import org.pytorch.Module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ObjectDetectionTheme {

                var moduleName by remember {
                    mutableStateOf("BasicModel")
                }
                val module: Module = LiteModuleLoader.loadModuleFromAsset(assets, "basicModel.pt")
                val navController = rememberNavController()

                Scaffold(topBar = {
                    AppBar(navController = navController)
                }) { contentPadding ->
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
    ObjectDetectionTheme {
    }

}