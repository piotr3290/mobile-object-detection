package com.example.objectdetection

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.scale

@OptIn(ExperimentalTextApi::class)
@Composable
fun ResultView(
    image: Bitmap,
    results: ArrayList<Result>,
    scale: Float,
    srcOffset: IntOffset = IntOffset.Zero,
    srcSize: IntSize = IntSize((image.width * scale).toInt(), (image.height * scale).toInt()),
    imageBitmap: ImageBitmap = image.scale(width = srcSize.width, height = srcSize.height).asImageBitmap(),
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {
    Canvas(
        modifier = Modifier
            .width(srcSize.width.pxToDp())
            .height(srcSize.height.pxToDp()),
        onDraw = {

            drawImage(
                imageBitmap,
                srcOffset,
                srcSize
            )

            for (result in results) {
                val measuredText =
                    textMeasurer.measure(AnnotatedString(YoloV5Classes.CLASSES[result.classIndex]))
                val offset = Offset(result.rect.left.toFloat(), result.rect.top.toFloat())

                drawRect(
                    color = Color.Magenta,
                    size = Size(
                        width = result.rect.width().toFloat(),
                        height = result.rect.height().toFloat()
                    ),
                    topLeft = offset,
                    style = Stroke(5.0f)
                )

                drawRect(
                    color = Color.Magenta,
                    topLeft = offset,
                    size = measuredText.size.toSize()
                )
                drawText(
                    measuredText,
                    topLeft = offset
                )
            }
        })
}