package com.example.objectdetection

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import android.graphics.Bitmap


class ObjectDetector(
    private val model: Model,
    private val updateScale: ObjectDetector.(Bitmap) -> Unit,
    private val onAnalysis: (ArrayList<Result>) -> Unit
) : ImageAnalysis.Analyzer {

    var scale: Float = 1.0f

    @WorkerThread
    override fun analyze(imageProxy: ImageProxy) {
        try {
            val bitmap = imageProxyToBitmap(imageProxy)
            updateScale(bitmap)
            val results = recognize(this.model, bitmap, scale)
            onAnalysis(results)
        } catch (exception: Exception) {
            Log.v("Error", exception.toString())
        } finally {
            imageProxy.close()
        }
    }

    private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        return image.toBitmap()!!.rotate(image.imageInfo.rotationDegrees.toFloat())
    }

}