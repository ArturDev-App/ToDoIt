package com.example.todoit.presentacion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonNote(
    textTitle: String, text: String,
    onCancelClick: () -> Unit,
    isEditing: Boolean,
    onEliminatedClick: () -> Unit,
    onSaveNote: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        FilledTonalButton(onClick = { onCancelClick() }) {
            Text("Cancelar")
        }
        if (isEditing){
            ElevatedButton(
                onClick = { showDeleteDialog = true }
            ) {
                Text("Eliminar")
            }
        }

        Button(
            onClick = { onSaveNote() },
            enabled = textTitle.isNotBlank() && text.isNotBlank()
        ) {
            Text("Guardar")
        }
        // Diálogo de confirmación para eliminar
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar nota") },
                text = { Text("¿Estás seguro de que quieres eliminar esta nota? Esta acción no se puede deshacer.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            onEliminatedClick()
                        }
                    ) {
                        Text("Eliminar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}