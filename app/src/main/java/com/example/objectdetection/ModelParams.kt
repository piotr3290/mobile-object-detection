package com.example.objectdetection

sealed class ModelParams(
    val name: String, val assetName: String,
    val classes: List<String>,
    val imageInputWidth: Int,
    val imageInputHeight: Int,
    val outputRows: Int,
    val outputColumns: Int,
)

object YOLOv5 : ModelParams(
    "YOLOv5",
    "yolov5s.torchscript.ptl",
    YoloClasses.CLASSES,
    640,
    640,
    25200,
    85
)

object YOLOv7 : ModelParams(
    "YOLOv7",
    "yolov7.torchscript.ptl",
    YoloClasses.CLASSES,
    640,
    640,
    25200,
    85
)