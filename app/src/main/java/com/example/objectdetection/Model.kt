package com.example.objectdetection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.pytorch.Module

class Model(name: String, module: Module, classes: List<String>) {
    var name by mutableStateOf(name)
    var module by mutableStateOf(module)
    var classes by mutableStateOf(classes)
}