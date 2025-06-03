package com.example.todoit.presentacion.components

import androidx.compose.material3.AssistChip

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AssistChipElement(
    onClickChip: () -> Unit,
    nameChip: String,
    icName: ImageVector,
    contentDescription: String,
    customIcon: (@Composable () -> Unit)? = null
) {
    AssistChip(
        onClick = onClickChip,
        label = { Text(nameChip) },
        leadingIcon = {
           customIcon?.invoke() ?:
            Icon(
                imageVector = icName,
                contentDescription = contentDescription
            )
        }
    )
}
