package com.example.objectdetection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.pytorch.Module

class Model(
    module: Module,
    modelParams: ModelParams
) {
    var module by mutableStateOf(module)
    var name by mutableStateOf(modelParams.name)
    var classes by mutableStateOf(modelParams.classes)
    var imageInputWidth by mutableStateOf(modelParams.imageInputWidth)
    var imageInputHeight by mutableStateOf(modelParams.imageInputHeight)
    var outputRows by mutableStateOf(modelParams.outputRows)
    var outputColumns by mutableStateOf(modelParams.outputColumns)
}