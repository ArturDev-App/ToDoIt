package com.example.todoit.model
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class AssistChipElementData(
    val name: String,
    val icon : ImageVector,
    val contentDescription: String,
    val costumeIcon: @Composable (() -> Unit)? = null,
    val onClick: () -> Unit
)

