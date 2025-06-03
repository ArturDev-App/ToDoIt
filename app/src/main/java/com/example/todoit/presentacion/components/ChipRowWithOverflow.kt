package com.example.todoit.presentacion.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoit.model.AssistChipElementData

@Composable
fun ChipRowScrollable(
    chipItems: List<AssistChipElementData>,
    selectPriorityText: String,
    selectTagText: String,
    selectStatusText: String,
    priorityIcon: (@Composable () -> Unit)? = null,
    tagIcon: (@Composable () -> Unit)? = null,
    statusIcon: (@Composable () -> Unit)? = null
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chipItems.forEach { chip ->
            // Determinar qué icono personalizado usar basado en el nombre del chip
            val customIcon = when (chip.name) {
                selectPriorityText -> priorityIcon
                selectTagText -> tagIcon
                selectStatusText -> statusIcon
                else -> chip.costumeIcon // Usar el icono personalizado del chip si existe
            }

            AssistChipElement(
                onClickChip = chip.onClick,
                nameChip = chip.name,
                icName = chip.icon,
                contentDescription = chip.contentDescription,
                customIcon = customIcon
            )
        }
    }
}