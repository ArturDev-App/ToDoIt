import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoit.presentacion.components.PartialBottomSheet
import com.example.todoit.presentacion.components.PrimaryMenu
import com.example.todoit.presentacion.screens.home.BottomNavMenu
import com.example.todoit.viewmodel.BottomSheetViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffoldScreen(
    showFab: Boolean = true,
    maintitle: String?,
    viewModel: BottomSheetViewModel = viewModel(),
    content: @Composable (PaddingValues) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    maintitle?.let {
                        Text(text = it)
                    }
                },
                actions = { PrimaryMenu() },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFFC6DCFF))
            )

        },
        bottomBar = {
            BottomAppBar(containerColor = Color(0xFF96AAEE)) {
                BottomNavMenu()
            }
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    onClick = { viewModel.openBottomSheet() },
                    shape = RoundedCornerShape(50),
                    containerColor = Color(0xFF6631D7),
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add note",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues) // Aquí va el contenido personalizado
        PartialBottomSheet(
            isEditing = viewModel.isEditing,
            showBottomSheet = viewModel.showBottomSheet,
            onDismiss = { viewModel.closeBottomSheet() },
            sheetState
        )
    }
}











