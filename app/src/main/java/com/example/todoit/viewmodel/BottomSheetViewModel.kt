package com.example.todoit.viewmodel

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.todoit.R
import com.example.todoit.model.ChipsMenuElement
import com.example.todoit.model.Note
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Locale

class BottomSheetViewModel : ViewModel() {
    //estado para abrir partialBottomSheet
    var showBottomSheet by mutableStateOf(false)
        private set
    // Indica si el BottomSheet está en modo de edición.
    // Si es true, se abrirá con la información ya cargada (modo edición).
    // Si es false, el BottomSheet se abre vacío para crear un nuevo elemento.
    var isEditing by mutableStateOf(false)
        private set

    var textTitle by mutableStateOf("")
        private set

    var textDesc by mutableStateOf("")
        private set

    var textDate by mutableStateOf("")
        private set

    var currentNoteId by mutableStateOf<String?>(null)
        private  set
    // Estados para cada dropdown
    var priorityExpanded by mutableStateOf(false)
        private set

    var tagExpanded by mutableStateOf(false)
        private set

    var statusExpanded by mutableStateOf(false)
        private set

    // Textos seleccionados
    var selectPriorityText by mutableStateOf("Prioridad")
        private set

    var selectTagText by mutableStateOf("Etiqueta")
        private set

    var selectStatusText by mutableStateOf("Estado")
        private set

    // Funciones básicas
    fun onTitleChange(newTitle: String) {
        textTitle = newTitle
    }

    fun onDescChange(newDesc: String) {
        textDesc = newDesc
    }

    fun onDatePartial(date: String){
        textDate = date
    }
    //Funciones para manejar la hoja partial
    fun openBottomSheet(){
        showBottomSheet = true
    }
    fun closeBottomSheet(){
        showBottomSheet = false
    }
    fun noteIsEditing (){
        isEditing = true
    }
    fun noteNoEditing (){
        isEditing = false
    }

    // Funciones para manejar los dropdowns
    fun onPriorityExpandedChange(value: Boolean) {
        priorityExpanded = value
    }

    fun onTagExpandedChange(value: Boolean) {
        tagExpanded = value
    }

    fun onStatusExpandedChange(value: Boolean) {
        statusExpanded = value
    }

    // Funciones para seleccionar items
    fun onPrioritySelected(text: String) {
        selectPriorityText = text
        priorityExpanded = false
    }

    fun onTagSelected(text: String) {
        selectTagText = text
        tagExpanded = false
    }

    fun onStatusSelected(text: String) {
        selectStatusText = text
        statusExpanded = false
    }

    fun initFromNote(note: Note) {
        currentNoteId = note.notaId
        textTitle = note.title
        textDesc = note.description
        textDate = formatTimestampToDateString(note.date)
        selectPriorityText = note.priority
        selectTagText = note.label
        selectStatusText = note.status
        priorityExpanded = false
        tagExpanded = false
        statusExpanded = false
    }

    fun buildNoteFromInputs(
        notaId : String ="",
        idUser: String = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    ): Note{
        return Note(
            notaId = notaId,
            idUsuario =idUser ,
            title = textTitle,
            description = textDesc,
            date = parseDateStringToTimestamp(textDate),
            priority = selectPriorityText,
            label = selectTagText,
            status = selectStatusText
        )
    }

    fun generateDocumentId(): String {
        return Firebase.firestore.collection("notes").document().id
    }

    fun resetBottomSheet() {
        currentNoteId = null
        textTitle = ""
        textDesc = ""
        textDate = "Fecha"
        selectPriorityText = "Prioridad"
        selectTagText = "Etiqueta"
        selectStatusText = "Estado"
        priorityExpanded = false
        tagExpanded = false
        statusExpanded = false
    }

    fun formatTimestampToDateString(timestamp: Timestamp?): String {
        return timestamp?.toDate()?.let { date ->
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.format(date)
        } ?: "Fecha"
    }

    fun parseDateStringToTimestamp(dateString: String): Timestamp? {
        return try {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = formatter.parse(dateString)
            Timestamp(date!!)
        } catch (e: Exception) {
            null
        }
    }

    // Funciones para obtener los elementos de menú
    fun getPriorityMenuItems(): List<ChipsMenuElement> {
        return listOf(
            ChipsMenuElement(
                "Alta",
            ) {
                Icon(
                    painter = painterResource(R.drawable.flag_solid),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
            },
            ChipsMenuElement(
                "Media",
            ) {
                Icon(
                    painter = painterResource(R.drawable.flag_solid),
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(20.dp)
                )
            },
            ChipsMenuElement(
                "Baja",
            ) {
                Icon(
                    painter = painterResource(R.drawable.flag_solid),
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(20.dp)
                )
            }
        )
    }

    fun getTagMenuItems(): List<ChipsMenuElement> {
        return listOf(
            ChipsMenuElement(
                "Laboral",
            ) {
                Icon(
                    painter = painterResource(R.drawable.briefcase_solid),//portafolio
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(15.dp)
                )
            },
            ChipsMenuElement(
                "Personal",
            ) {
                Icon(
                    painter = painterResource(R.drawable.circle_user_regular),//user
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(15.dp)
                )
            },
            ChipsMenuElement(
                "Hogar",
            ) {
                Icon(
                    painter = painterResource(R.drawable.house_solid),//home
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(15.dp)
                )
            },
            ChipsMenuElement(
                "Contable",
            ) {
                Icon(
                    painter = painterResource(R.drawable.seedling_solid),//finanzas
                    contentDescription = null,
                    tint = Color.Cyan,
                    modifier = Modifier.size(15.dp)
                )
            },
            ChipsMenuElement(
                "Viajes",
            ) {
                Icon(
                    painter = painterResource(R.drawable.plane_solid),//avion
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(15.dp)
                )
            },
            ChipsMenuElement(
                "Clientes",
            ) {
                Icon(
                    painter = painterResource(R.drawable.dollar_sign_solid),//cobranza
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(15.dp)
                )
            },
            ChipsMenuElement(
                "Academico",
            ) {
                Icon(
                    painter = painterResource(R.drawable.school_solid),//libro
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(15.dp)
                )
            },


            )
    }

    fun getStatusMenuItems(): List<ChipsMenuElement> {
        return listOf(
            ChipsMenuElement(
                "Pendiente",
            ) {
                Icon(
                    painter = painterResource(R.drawable.circle_xmark_solid),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
            },
            ChipsMenuElement(
                "En Progreso",
            ) {
                Icon(
                    painter = painterResource(R.drawable.rotate_right_solid),
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(20.dp)
                )
            },
            ChipsMenuElement(
                "Completado",
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(20.dp)
                )
            }
        )
    }

    // Funciones para obtener los iconos según la selección
    fun getPriorityIcon(priority: String): @Composable () -> Unit {
        return when (priority) {
            "Alta" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.flag_solid),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            "Media" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.flag_solid),
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            "Baja" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.flag_solid),
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            else -> {
                {
                    Icon(Icons.Default.Star, contentDescription = "Prioridad")
                }
            }
        }
    }

    fun getTagIcon(tag: String): @Composable () -> Unit {
        return when (tag) {
            "Laboral" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.briefcase_solid),//portafolio
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            "Personal" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.circle_user_regular),//user
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            "Hogar" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.house_solid),//home
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            "Contable" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.seedling_solid),//finanzas
                        contentDescription = null,
                        tint = Color.Cyan,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            "Viajes" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.plane_solid),//avion
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            "Clientes" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.dollar_sign_solid),//cobranza
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            "Academico" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.school_solid),//libro
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            else -> {
                {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Etiqueta")
                }
            }
        }
    }

    fun getStatusIcon(status: String): @Composable () -> Unit {
        return when (status) {
            "Pendiente" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.circle_xmark_solid),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            "En Progreso" -> {
                {
                    Icon(
                        painter = painterResource(R.drawable.rotate_right_solid),
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            "Completado" -> {
                {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            else -> {
                {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Estado")
                }
            }
        }
    }
}
