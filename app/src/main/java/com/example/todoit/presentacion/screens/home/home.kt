package com.example.todoit.presentacion.screens.home

import BaseScaffoldScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoit.presentacion.components.ChipGroup
import com.example.todoit.presentacion.components.NoteElement
import com.example.todoit.viewmodel.BottomSheetViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    viewModelPartial: BottomSheetViewModel = viewModel()
) {
    BaseScaffoldScreen(
        showFab = true,
        maintitle = null
    ) { padding ->
        // Box principal para manejar el DropdownMenu correctamente
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFC6DCFF)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ChipGroup()
                }

                val scroll = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxSize()
                        .verticalScroll(scroll),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Mostrar las notas desde el ViewModel
                    viewModel.notes.forEach { note ->
                        Box(contentAlignment = Alignment.CenterEnd) {
                            NoteElement(
                                title = note.title,
                                date = viewModel.formatDate(note.date),
                                priority = note.priority,
                                status = note.status,
                                statusColor = viewModel.getStatusColor(note.status),
                                onStatusClick = { viewModel.openDropdown(note.notaId) },
                                onNoteClick = {
                                    viewModel.selectNoteForEdit(note)
                                    viewModelPartial.noteIsEditing()
                                    viewModelPartial.openBottomSheet()
                                    viewModelPartial.initFromNote(note)
                                }
                            )
                            Column(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                                // DropdownMenu asociado a cada nota
                                DropdownMenu(
                                    expanded = viewModel.expandedNoteId == note.notaId,
                                    onDismissRequest = { viewModel.closeDropdown() }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Completado") },
                                        onClick = {
                                            viewModel.updateNoteStatus(note.notaId, "Completado")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("En Progreso") },
                                        onClick = {
                                            viewModel.updateNoteStatus(note.notaId, "En Progreso")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Pendiente") },
                                        onClick = {
                                            viewModel.updateNoteStatus(note.notaId, "Pendiente")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}