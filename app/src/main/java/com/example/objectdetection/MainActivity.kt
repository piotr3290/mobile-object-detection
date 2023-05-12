package com.example.objectdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.objectdetection.ui.theme.AppTheme
import org.pytorch.LiteModuleLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                var model by remember {
                    mutableStateOf(
                        Model(
                            LiteModuleLoader.loadModuleFromAsset(assets, YOLOv5.assetName),
                            YOLOv5
                        )
                    )
                }
                Scaffold(
                    topBar = { AppBar(navController = navController) }
                ) { contentPadding ->
                    Navigation(
                        navController = navController,
                        model = model,
                        modifier = Modifier.padding(contentPadding),
                        onModelChange = {
                            model = Model(
                                LiteModuleLoader.loadModuleFromAsset(assets, it.assetName),
                                it
                            )
                        }
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