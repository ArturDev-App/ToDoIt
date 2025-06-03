package com.example.todoit.model

import com.google.firebase.Timestamp

data class Note(
    val notaId: String = "",               // ID opcional para la nota (puede ser el documentId si lo necesitas)
    val idUsuario: String = "",            // ID del usuario autenticado (FirebaseAuth.currentUser?.uid)
    val title: String = "",                // titulo de la nota
    val description: String = "",          //descripcion de la nota
    val date: Timestamp? = null,           // Fecha seleccionada (mejor que String)
    val priority: String = "",             // "Alta", "Media", "Baja"
    val reminder: Timestamp? = null,       // Usado para notificaciones o alarmas
    val label: String = "",                // Etiqueta  como "laboral","academico", "financiero" etc.
    val status: String = ""                // "Pendiente", "En progreso", "Completado"
)