package com.example.todoit.presentacion.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoit.model.ChipsMenuElement

@Composable
fun DropDownMenuGeneric(
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    onItemSelected: (String) -> Unit, // Simplificado: solo pasa el texto
    menuItems: List<ChipsMenuElement>,
    containerColor: Color = Color.DarkGray,
    textColor: Color = Color.White,
    showDivider: Boolean = true
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpanded(false) },
        containerColor = containerColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.name, color = textColor, fontSize = 16.sp) },
                onClick = {
                    onItemSelected(item.name) // Solo pasa el texto
                    onExpanded(false)
                },
                leadingIcon = item.icon
            )
        }

        if (showDivider) {
            HorizontalDivider(thickness = 5.dp)
        }
    }
}