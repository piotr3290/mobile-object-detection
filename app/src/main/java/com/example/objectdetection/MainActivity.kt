package com.example.objectdetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.objectdetection.ui.theme.ObjectDetectionTheme
import org.pytorch.*
import org.pytorch.torchvision.TensorImageUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module: Module = LiteModuleLoader.loadModuleFromAsset(assets, "model.pt")


//
//        for (i in scores.indices) {
//            Log.v("tag", "${Classes.CLASSES[i]}: ${scores[i]}")
//            if (scores[i] > maxScore) {
//                maxScore = scores[i]
//                maxScoreIndex = i
//            }
//        }


        setContent {
            ObjectDetectionTheme {

                var selectedImageUri by remember {
                    mutableStateOf<Uri?>(null)
                }

                var imageClass by remember {
                    mutableStateOf("")
                }

                val imagePickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        run {
                            selectedImageUri = uri
                            Log.i("i", uri.toString())
                            if (uri != null) {
                                imageClass = recognize(
                                    module = module,
                                    bitmap = BitmapFactory.decodeStream(
                                        applicationContext.contentResolver.openInputStream(
                                            uri
                                        )
                                    )
                                )
                            }
                        }
                    }
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(onClick = {
                                    imagePickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }) {
                                    Text(text = "Pick image")
                                }
                            }
                        }

                        item {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Selected image",
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        item {
                            Text(text = imageClass)
                        }

                    }

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

fun recognize(module: Module, bitmap: Bitmap): String {
    val inputTensor: Tensor = TensorImageUtils.bitmapToFloat32Tensor(
        bitmap,
        TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
        TensorImageUtils.TORCHVISION_NORM_STD_RGB,
        MemoryFormat.CHANNELS_LAST
    )

    val outputTensor: Tensor = module.forward(IValue.from(inputTensor)).toTensor()
    val scores: FloatArray = outputTensor.dataAsFloatArray
    var maxScore: Float = scores.max()

    var maxScoreIndex: Int = scores.indexOfFirst {
        it == maxScore
    }

    Log.v("tag", "${Classes.CLASSES[maxScoreIndex]} - $maxScore")
    return "${Classes.CLASSES[maxScoreIndex]} - $maxScore"
}
