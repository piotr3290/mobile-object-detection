package com.example.objectdetection

import android.graphics.Rect
import java.util.*
import kotlin.math.max
import kotlin.math.min


val NO_MEAN_RGB = floatArrayOf(0.0f, 0.0f, 0.0f)
val NO_STD_RGB = floatArrayOf(1.0f, 1.0f, 1.0f)

const val THRESHOLD = 0.30f
const val NMS_LIMIT = 15 // non-maximum suppression

fun nonMaxSuppression(boxes: ArrayList<Result>, limit: Int, threshold: Float): ArrayList<Result> {
    boxes.sort()
    boxes.reverse()

    val selectedResults = ArrayList<Result>()
    val active = BooleanArray(boxes.size)
    Arrays.fill(active, true)
    var numActive = active.size

    var done = false


    var i = 0
    while (i < boxes.size && !done) {
        if (active[i]) {
            val boxA = boxes[i]
            selectedResults.add(boxA)

            if (selectedResults.size >= limit) break

            for (j in i + 1 until boxes.size) {
                if (active[j]) {
                    val boxB = boxes[j]
                    if (intersectionOverUnion(boxA.rect, boxB.rect) > threshold) {
                        active[j] = false
                        numActive -= 1
                        if (numActive <= 0) {
                            done = true
                            break
                        }
                    }
                }
            }
        }
        i++
    }


    return selectedResults
}

fun intersectionOverUnion(rectA: Rect, rectB: Rect): Float {
    val areaA = ((rectA.right - rectA.left) * (rectA.bottom - rectA.top)).toFloat()
    if (areaA <= 0.0) return 0.0f

    val areaB = ((rectB.right - rectB.left) * (rectB.bottom - rectB.top)).toFloat()
    if (areaB <= 0.0) return 0.0f

    val intersectionMinX = max(rectA.left, rectB.left).toFloat()
    val intersectionMinY = max(rectA.top, rectB.top).toFloat()
    val intersectionMaxX = min(rectA.right, rectB.right).toFloat()
    val intersectionMaxY = min(rectA.bottom, rectB.bottom).toFloat()
    val intersectionArea = max(intersectionMaxY - intersectionMinY, 0f) *
            max(intersectionMaxX - intersectionMinX, 0f)

    return intersectionArea / (areaA + areaB - intersectionArea)
}

fun outputsToNMSPredictions(
    outputs: FloatArray,
    imgScaleX: Float,
    imgScaleY: Float,
    ivScaleX: Float,
    ivScaleY: Float,
    startX: Float,
    startY: Float,
    model: Model
): ArrayList<Result> {
    val results = ArrayList<Result>()

    for (i in 0 until model.outputRows) {
        if (outputs[i * model.outputColumns + 4] > THRESHOLD) {
            val x = outputs[i * model.outputColumns]
            val y = outputs[i * model.outputColumns + 1]
            val width = outputs[i * model.outputColumns + 2]
            val height = outputs[i * model.outputColumns + 3]

            val left = imgScaleX * (x - width / 2)
            val top = imgScaleY * (y - height / 2)
            val right = imgScaleX * (x + width / 2)
            val bottom = imgScaleY * (y + height / 2)

            var maxValue = outputs[i * model.outputColumns + 5]
            var classIndex = 0

            for (j in 0 until model.outputColumns - 5) {
                if (outputs[i * model.outputColumns + 5 + j] > maxValue) {
                    maxValue = outputs[i * model.outputColumns + 5 + j]
                    classIndex = j
                }
            }

            val rect = Rect(
                (startX + ivScaleX * left).toInt(),
                (startY + top * ivScaleY).toInt(),
                (startX + ivScaleX * right).toInt(),
                (startY + ivScaleY * bottom).toInt()
            )

            val result = Result(classIndex, outputs[i * model.outputColumns + 4], rect)
            results.add(result)
        }
    }

    return nonMaxSuppression(results, NMS_LIMIT, THRESHOLD)
}