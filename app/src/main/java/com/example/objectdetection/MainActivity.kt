package com.example.objectdetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.objectdetection.ui.theme.ObjectDetectionTheme
import org.pytorch.*
import org.pytorch.torchvision.TensorImageUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bitmap: Bitmap = BitmapFactory.decodeStream(assets.open("image.jpg"))
        val module: Module = LiteModuleLoader.loadModuleFromAsset(assets, "model.pt")
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
//
//        for (i in scores.indices) {
//            Log.v("tag", "${Classes.CLASSES[i]}: ${scores[i]}")
//            if (scores[i] > maxScore) {
//                maxScore = scores[i]
//                maxScoreIndex = i
//            }
//        }
        Log.v("tag", "${Classes.CLASSES[maxScoreIndex]} - $maxScore")


        setContent {
            ObjectDetectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

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
