package com.example.objectdetection

import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawBoundingBoxes(boundingBoxes: List<Result>, textMeasurer: TextMeasurer) {
    boundingBoxes.forEach {
        drawBoundingBox(it.rect, it.classLabel, textMeasurer)
    }
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawBoundingBox(rect: Rect, label: String, textMeasurer: TextMeasurer) {
    val measuredText = textMeasurer.measure(AnnotatedString(label))
    val offset = Offset(rect.left.toFloat(), rect.top.toFloat())

    drawRect(
        color = Color.Magenta,
        size = Size(
            width = rect.width().toFloat(),
            height = rect.height().toFloat()
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