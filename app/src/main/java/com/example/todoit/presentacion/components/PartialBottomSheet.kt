package com.example.todoit.presentacion.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoit.model.AssistChipElementData
import com.example.todoit.presentacion.screens.home.HomeViewModel
import com.example.todoit.viewmodel.BottomSheetViewModel

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    isEditing: Boolean,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    sheetState: SheetState, viewModelHome: HomeViewModel = viewModel(),
    viewModel: BottomSheetViewModel = viewModel(),
    initialDate: String? = null
) {
    // Este LaunchedEffect se ejecuta cada vez que showBottomSheet cambia
    LaunchedEffect(showBottomSheet, isEditing) {
        if (showBottomSheet && !isEditing) {
            // Solo resetea cuando se abre el bottom sheet y NO está editando
            viewModel.resetBottomSheet()
        }
    }

    // También resetea cuando se cierra el bottom sheet
    LaunchedEffect(showBottomSheet) {
        if (!showBottomSheet) {
            // Resetea cuando se cierra el bottom sheet
            viewModel.resetBottomSheet()
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = onDismiss,
        ) {
            val maxLinesAllowed = 3
            val maxChars = 150

            val textTitle = viewModel.textTitle
            val textDesc = viewModel.textDesc

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 15.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = textTitle,
                    onValueChange = viewModel::onTitleChange,
                    label = { Text("Titulo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors()
                )

                TextField(
                    value = textDesc,
                    onValueChange = {
                        val currentLines = it.count { c -> c == '\n' } + 1
                        if (currentLines <= maxLinesAllowed && it.length <= maxChars) {
                            viewModel.onDescChange(it)
                        }
                    },
                    label = { Text("Descripción") },
                    maxLines = maxLinesAllowed,
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color.Gray,
                                start = androidx.compose.ui.geometry.Offset(0f, y),
                                end = androidx.compose.ui.geometry.Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        },
                    colors = customTextFieldColors()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "${textDesc.length} / $maxChars caracteres",
                    color = if (textDesc.length < 140) Color.Gray else Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )

                val (_, _, openDatePicker) = selectAndFormatDate(initialDate)

                // Dropdowns genéricos
                DropDownMenuGeneric(
                    expanded = viewModel.priorityExpanded,
                    onExpanded = viewModel::onPriorityExpandedChange,
                    onItemSelected = viewModel::onPrioritySelected, // Simplificado
                    menuItems = viewModel.getPriorityMenuItems()
                )

                DropDownMenuGeneric(
                    expanded = viewModel.tagExpanded,
                    onExpanded = viewModel::onTagExpandedChange,
                    onItemSelected = viewModel::onTagSelected, // Simplificado
                    menuItems = viewModel.getTagMenuItems()
                )

                DropDownMenuGeneric(
                    expanded = viewModel.statusExpanded,
                    onExpanded = viewModel::onStatusExpandedChange,
                    onItemSelected = viewModel::onStatusSelected, // Simplificado
                    menuItems = viewModel.getStatusMenuItems()
                )

                val chipOptions = listOf(
                    AssistChipElementData(
                        name = viewModel.textDate,
                        icon = Icons.Default.DateRange,
                        contentDescription = "Seleccionar Fecha",
                        onClick = openDatePicker
                    ),
                    AssistChipElementData(
                        name = viewModel.selectPriorityText,
                        icon = Icons.Default.Star,
                        contentDescription = "Establecer Prioridad",
                        costumeIcon = null, // Se maneja dinámicamente en ChipRowScrollable
                        onClick = { viewModel.onPriorityExpandedChange(true) }
                    ),
                    AssistChipElementData(
                        name = "Recordatorio",
                        icon = Icons.Default.Notifications,
                        contentDescription = "Establecer Recordatorio",
                        onClick = { /* TODO */ }
                    ),
                    AssistChipElementData(
                        name = viewModel.selectTagText,
                        icon = Icons.Default.ArrowDropDown,
                        contentDescription = "Establecer Etiqueta",
                        costumeIcon = null, // Se maneja dinámicamente en ChipRowScrollable
                        onClick = { viewModel.onTagExpandedChange(true) }
                    ),
                    AssistChipElementData(
                        name = viewModel.selectStatusText,
                        icon = Icons.Default.CheckCircle,
                        contentDescription = "Establecer Estado",
                        costumeIcon = null, // Se maneja dinámicamente en ChipRowScrollable
                        onClick = { viewModel.onStatusExpandedChange(true) }
                    )
                )

                ChipRowScrollable(
                    chipItems = chipOptions,
                    selectPriorityText = viewModel.selectPriorityText,
                    selectTagText = viewModel.selectTagText,
                    selectStatusText = viewModel.selectStatusText,
                    priorityIcon = viewModel.getPriorityIcon(viewModel.selectPriorityText),
                    tagIcon = viewModel.getTagIcon(viewModel.selectTagText),
                    statusIcon = viewModel.getStatusIcon(viewModel.selectStatusText)
                )

                ButtonNote(
                    textTitle = textTitle,
                    text = textDesc,
                    isEditing = isEditing,
                    onCancelClick = onDismiss,
                    onEliminatedClick = {
                            viewModel.currentNoteId?.let {noteId ->
                                viewModelHome.deleteNote(noteId)
                                viewModel.closeBottomSheet()
                                viewModel.noteNoEditing()
                            }
                    },
                    onSaveNote = {
                        if (!isEditing) {
                            //crear nueva nota
                            val generatedId = viewModel.generateDocumentId()
                            val note = viewModel.buildNoteFromInputs(generatedId)
                            viewModelHome.addNote(note)
                            viewModel.closeBottomSheet()
                            viewModel.noteNoEditing()
                        }else{
                            // Actualizar nota existente
                            val selectedNote = viewModelHome.selectedNoteForEdit
                            if (selectedNote != null) {
                                val updatedNote = viewModel.buildNoteFromInputs(
                                    notaId = selectedNote.notaId,
                                    idUser = selectedNote.idUsuario
                                )
                                viewModelHome.updateNote(updatedNote)
                                viewModel.closeBottomSheet()
                                viewModel.noteNoEditing()
                            }
                        }
                    }
                )
            }
        }
    }
}

//valores personalisados de textField utilizados
@Composable
fun customTextFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    cursorColor = Color.Black,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black
)