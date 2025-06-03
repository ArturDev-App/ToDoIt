package com.example.todoit.presentacion.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(state : DatePickerState, showDialogDate: Boolean, confirmButton : () -> Unit, cancelButton: ()-> Unit) {
    if (showDialogDate) {
        DatePickerDialog(
            onDismissRequest = { showDialogDate},
            confirmButton = {
                Button(onClick = {confirmButton()}) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = {cancelButton()}) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = state
            )
        }
    }
}