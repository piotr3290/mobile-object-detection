package com.example.objectdetection

import android.Manifest
import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@OptIn(ExperimentalTextApi::class)
@Composable
fun CameraScreen(model: Model) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val scaleType = PreviewView.ScaleType.FIT_CENTER
    val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    val results: ArrayList<Result> = ArrayList()

    var resultsRemember by remember {
        mutableStateOf(results)
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val textMeasurer = rememberTextMeasurer()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    var boxWidth by remember {
        mutableStateOf(0)
    }

    var boxHeight by remember {
        mutableStateOf(0)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

        ) {
        item {
            if (hasCameraPermission) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(boxHeight.pxToDp())
                ) {
                    boxWidth = this.maxWidth.dpToPx().toInt()

                    AndroidView(
                        factory = { context2 ->
                            val previewView = PreviewView(context2).apply {
                                this.scaleType = scaleType
                                this.layoutParams = layoutParams
                            }

                            val preview = Preview.Builder()
                                .build()
                                .also {
                                    it.setSurfaceProvider(previewView.surfaceProvider)
                                }

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context2),
                                ObjectDetector(model, {
                                    boxHeight =
                                        (it.height.toFloat() * boxWidth.toFloat() / it.width.toFloat()).toInt()
                                    this.scale = boxHeight.toFloat() / it.height.toFloat()
                                }) {
                                    resultsRemember = it
                                }
                            )

                            try {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageAnalysis
                                )

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            previewView
                        })

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawBoundingBoxes(resultsRemember, textMeasurer)
                    }
                }
            }
        }
    }

}