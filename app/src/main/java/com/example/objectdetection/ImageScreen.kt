package com.example.objectdetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils

@OptIn(ExperimentalTextApi::class)
@Composable
fun ImageScreen(model: Model) {
    val applicationContext = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val results: ArrayList<Result> = ArrayList()

    var resultsRemember by remember {
        mutableStateOf(results)
    }

    var imageBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var displayScale by remember {
        mutableStateOf(1.0f)
    }

    BoxWithConstraints {

        val maxWidth = this.maxWidth.dpToPx()

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                run {
                    selectedImageUri = uri
                    if (uri != null) {

                        imageBitmap = BitmapFactory.decodeStream(
                            applicationContext.contentResolver.openInputStream(
                                uri
                            )
                        )

                        if (imageBitmap != null) {

                            displayScale =
                                if (maxWidth >= imageBitmap!!.width.toFloat()) 1.0f
                                else maxWidth / imageBitmap!!.width.toFloat()


                            resultsRemember = recognize(
                                module = model.module,
                                bitmap = imageBitmap!!,
                                scale = displayScale
                            )

                        }
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
                if (imageBitmap != null) {

                    ResultView(
                        image = imageBitmap!!,
                        results = resultsRemember,
                        scale = displayScale,
                        classes = model.classes
                    )

                }

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

}

fun recognize(module: Module, bitmap: Bitmap, scale: Float): ArrayList<Result> {

    Log.v("scale", scale.toString())

    val resizedBitmap =
        Bitmap.createScaledBitmap(bitmap, IMAGE_INPUT_WIDTH, IMAGE_INPUT_HEIGHT, true)

    val inputTensor: Tensor = TensorImageUtils.bitmapToFloat32Tensor(
        resizedBitmap,
        NO_MEAN_RGB,
        NO_STD_RGB
    )

    val outputTuple = module.forward(IValue.from(inputTensor)).toTuple()

    val outputTensor: Tensor = outputTuple[0].toTensor()
    val scores: FloatArray = outputTensor.dataAsFloatArray

    val imgScaleX: Float = (bitmap.width.toFloat() / IMAGE_INPUT_WIDTH.toFloat())
    val imgScaleY: Float = (bitmap.height.toFloat() / IMAGE_INPUT_HEIGHT.toFloat())
    val ivScaleX: Float = scale
    val ivScaleY: Float = scale

    val results =
        outputsToNMSPredictions(scores, imgScaleX, imgScaleY, ivScaleX, ivScaleY, 0.0f, 0.0f)

    Log.v("results", results.toString())

    return results
}
