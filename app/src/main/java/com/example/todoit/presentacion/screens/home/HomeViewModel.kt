package com.example.todoit.presentacion.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.todoit.model.Note
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel : ViewModel() {
    // Lista de notas que se mostrará en la pantalla
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> = _notes

    // Estado para controlar qué dropdown está abierto
    var expandedNoteId by mutableStateOf<String?>(null)
        private set

    // Nota seleccionada para editar
    var selectedNoteForEdit by mutableStateOf<Note?>(null)
        private set

    // Estado de carga
    var isLoading by mutableStateOf(false)
        private set

    init {
        // Cargar notas reales desde Firestore
        loadNotesFromFirestore()
    }

    // Función para formatear el Timestamp a String
    fun formatDate(timestamp: Timestamp?): String {
        return timestamp?.let {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.format(it.toDate())
        } ?: ""
    }

    // Función para agregar una nueva nota
    fun addNote(note: Note) {
        _notes.add(note)

        val collection = Firebase.firestore.collection("notes")

        collection
            .document(note.notaId)
            .set(note)
            .addOnSuccessListener {
                Log.d("Firestore", "Nota guardada correctamente con ID: ${note.notaId}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al guardar la nota", e)
                // Si falla, removemos la nota de la lista local
                _notes.removeAll { it.notaId == note.notaId }
            }
    }

    fun updateNote(updatedNote: Note) {
        // Actualizar localmente
        val index = _notes.indexOfFirst { it.notaId == updatedNote.notaId }
        if (index != -1) {
            _notes[index] = updatedNote
        }

        // Actualizar en Firestore
        val collection = Firebase.firestore.collection("notes")
        collection
            .document(updatedNote.notaId)
            .set(updatedNote)
            .addOnSuccessListener {
                Log.d("Firestore", "Nota actualizada correctamente: ${updatedNote.notaId}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al actualizar la nota", e)
                // Si falla, revertir cambios locales
                loadNotesFromFirestore()
            }
    }

    fun updateNoteStatus(noteId: String, newStatus: String) {
        val index = _notes.indexOfFirst { it.notaId == noteId }
        if (index != -1) {
            val updatedNote = _notes[index].copy(status = newStatus)
            _notes[index] = updatedNote

            // Actualizar en Firestore
            Firebase.firestore.collection("notes")
                .document(noteId)
                .update("status", newStatus)
                .addOnSuccessListener {
                    Log.d("Firestore", "Estado actualizado correctamente")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error al actualizar estado", e)
                    // Revertir cambio local si falla
                    loadNotesFromFirestore()
                }
        }
        closeDropdown()
    }

    // Función para seleccionar una nota para editar
    fun selectNoteForEdit(note: Note) {
        selectedNoteForEdit = note
        // Aquí podrías abrir el BottomSheet o navegar a la pantalla de edición
    }

    // Función para abrir el dropdown de una nota específica
    fun openDropdown(noteId: String) {
        expandedNoteId = noteId
    }

    // Función para cerrar el dropdown
    fun closeDropdown() {
        expandedNoteId = null
    }

    // Función para obtener el color según el estado
    fun getStatusColor(status: String): androidx.compose.ui.graphics.Color {
        return when (status) {
            "Completado" -> androidx.compose.ui.graphics.Color.Green
            "En Progreso" -> androidx.compose.ui.graphics.Color.Yellow
            "Pendiente" -> androidx.compose.ui.graphics.Color.Red
            else -> androidx.compose.ui.graphics.Color.Gray
        }
    }

    // Función para obtener el color según la prioridad
    fun getPriorityColor(priority: String): androidx.compose.ui.graphics.Color {
        return when (priority) {
            "Alta" -> androidx.compose.ui.graphics.Color.Red
            "Media" -> androidx.compose.ui.graphics.Color.Yellow
            "Baja" -> androidx.compose.ui.graphics.Color.Green
            else -> androidx.compose.ui.graphics.Color.Gray
        }
    }

    // Función para cargar notas desde Firestore
    fun loadNotesFromFirestore() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.w("Firestore", "Usuario no autenticado")
            return
        }

        isLoading = true
        val collection = Firebase.firestore.collection("notes")

        collection
            .whereEqualTo("idUsuario", currentUser.uid)
            .addSnapshotListener { snapshot, error ->
                isLoading = false

                if (error != null) {
                    Log.e("Firestore", "Error al cargar notas", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _notes.clear()

                    for (document in snapshot.documents) {
                        try {
                            val note = document.toObject(Note::class.java)
                            if (note != null) {
                                _notes.add(note)
                            }
                        } catch (e: Exception) {
                            Log.e("Firestore", "Error al convertir documento: ${document.id}", e)
                        }
                    }

                    Log.d("Firestore", "Notas cargadas: ${_notes.size}")
                } else {
                    Log.d("Firestore", "Snapshot es null")
                }
            }
    }


    // Función para eliminar una nota
    fun deleteNote(noteId: String) {
        // Remover localmente
        _notes.removeAll { it.notaId == noteId }

        // Eliminar de Firestore
        Firebase.firestore.collection("notes")
            .document(noteId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Nota eliminada correctamente")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al eliminar nota", e)
                // Recargar datos si falla
                loadNotesFromFirestore()
            }
    }

    // Función para refrescar datos manualmente
    fun refreshNotes() {
        loadNotesFromFirestore()
    }
}