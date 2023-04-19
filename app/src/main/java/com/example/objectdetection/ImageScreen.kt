package com.example.objectdetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.pytorch.IValue
import org.pytorch.MemoryFormat
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils

@Composable
fun ImageScreen(module: Module) {
    val applicationContext = LocalContext.current
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

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Selected image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(text = imageClass)
        }

        item {
            Button(
                onClick = {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Upload image")
            }
        }
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
    val maxScore: Float = scores.max()

    val maxScoreIndex: Int = scores.indexOfFirst {
        it == maxScore
    }

    Log.v("tag", "${Classes.CLASSES[maxScoreIndex]} - $maxScore")
    return "${Classes.CLASSES[maxScoreIndex]} - $maxScore"
}
