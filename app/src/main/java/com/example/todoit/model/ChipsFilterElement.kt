package com.example.todoit.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

data class ChipsFilterElement(
    val name: String,
    val onClick: ()-> Unit
)
data class ChipsMenuElement(
    val name: String,
    val icon : @Composable () -> Unit
)
