package com.example.todoit.presentacion.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun PrimaryMenu(){
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(16.dp),
    ){
        IconButton(onClick = {expanded = !expanded}) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options", modifier = Modifier.size(100.dp))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            containerColor = Color.DarkGray
        ) {
            DropdownMenuItem(
                text = { Text(text = "Seleccionar", color = Color.White) },
                onClick = {/*TODO: ADD FUNCTIONALITY */}
            )
            DropdownMenuItem(
                text = { Text(text = "Help", color = Color.White)},
                onClick = {/*TODO: ADD FUNCTIONALITY */}
            )
        }
    }
}