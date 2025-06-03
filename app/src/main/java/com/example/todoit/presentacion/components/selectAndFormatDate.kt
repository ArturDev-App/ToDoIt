package com.example.todoit.presentacion.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoit.viewmodel.BottomSheetViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectAndFormatDate(initialDate: String? = null, viewModel: BottomSheetViewModel = viewModel()): Triple<String, Boolean, () -> Unit> {
    var showDialogDate by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(initialDate ?: "Fecha") }
    var selectedMillis by remember { mutableStateOf<Long?>(null) }
    //Controla el estado actual de la fecha y no permite regresar a fechas anteriores al usuario
    val state = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Convierte el UTC milliseconds a fecha local
                val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC"))  // Mantén en UTC para la comparación
                    .toLocalDate()

                // Obtén la fecha actual en UTC para comparación consistente
                val todayUtc = LocalDate.now(ZoneId.of("UTC"))

                // Permite fechas de hoy en adelante (incluye el día actual)
                return !selectedDate.isBefore(todayUtc)
            }
        }
    )
    //da formato a la fecha y la devuelve en date
    selectedMillis?.let { millis->
        val localDate = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
        date = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
        viewModel.onDatePartial(date)
    }
    //controla los botones de accion cancelar y guardar el DatePiker y el DatePikerDialog
    DatePickerModal(
        state = state,
        showDialogDate = showDialogDate,
        confirmButton = {
            selectedMillis = state.selectedDateMillis
            showDialogDate = false
        },
        cancelButton = { showDialogDate = false }
    )

    // Devuelve: texto de fecha, estado actual del modal y acción al tocar el chip
    return Triple(date, showDialogDate) { showDialogDate = true }
}