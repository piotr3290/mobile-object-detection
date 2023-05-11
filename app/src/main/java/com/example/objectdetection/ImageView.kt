package com.example.objectdetection

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.scale

@OptIn(ExperimentalTextApi::class)
@Composable
fun ImageView(
    image: Bitmap,
    results: ArrayList<Result>,
    scale: Float,
    srcSize: IntSize = IntSize((image.width * scale).toInt(), (image.height * scale).toInt()),
    imageBitmap: ImageBitmap = image.scale(width = srcSize.width, height = srcSize.height)
        .asImageBitmap(),
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {
    Canvas(
        modifier = Modifier
            .width(srcSize.width.pxToDp())
            .height(srcSize.height.pxToDp())
    ) {

        drawImage(
            imageBitmap,
            IntOffset.Zero,
            srcSize
        )

        drawBoundingBoxes(results, textMeasurer)
    }
}