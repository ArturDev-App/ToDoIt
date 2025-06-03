package com.example.todoit.presentacion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoit.model.ChipsFilterElement

@Composable
fun FilterChipElement(
    nameChip: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onSelected,
        label = {
            Text(nameChip)
        },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected) Color(0xFFDB8888) else Color.Gray,
        )
    )
}
@Composable
fun ChipGroup() {
    var selectedChip by remember { mutableStateOf<String?>(null) }

    val chipsFilters = listOf(
        ChipsFilterElement("Todos"){},
        ChipsFilterElement("Laboral"){},
        ChipsFilterElement("Personal"){},
        ChipsFilterElement("Hogar"){},
        ChipsFilterElement("Contable"){},
        ChipsFilterElement("Viajes"){},
        ChipsFilterElement("Clientes"){},
        ChipsFilterElement("Academico"){}
        )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chipsFilters.forEach { chip ->
            FilterChipElement(
                nameChip = chip.name,
                isSelected = selectedChip == chip.name,
                onSelected = { selectedChip = chip.name }
            )
        }
    }
}