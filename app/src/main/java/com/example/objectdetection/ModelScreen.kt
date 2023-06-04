package com.example.objectdetection

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable


@Composable
fun ModelScreen(
    selectedModel: Model,
    onModelSelected: (ModelParams) -> Unit
) {
    val models = listOf<ModelParams>(YOLOv5, YOLOv7)

    fun selectModel(modelParams: ModelParams) {
        onModelSelected(modelParams)
    }

    LazyColumn {
        item { Divider() }
        items(models.size) {
            ListItem(
                headlineContent = {
                    Text(text = models[it].name)
                },
                leadingContent = {
                    RadioButton(selected = (selectedModel.name == models[it].name), onClick = {selectModel(models[it])})
                },
            )
            Divider()
        }
    }
}