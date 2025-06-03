package com.example.todoit.presentacion.screens.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoit.AuthManager
import com.example.todoit.R
import com.example.todoit.presentacion.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    // Crear AuthManager y pasarlo al ViewModelFactory
    val authManager = remember { AuthManager(context) }
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(authManager)
    )

    val loginSuccess by viewModel.loginSuccess.collectAsState()

    // Launcher para Google Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleSignInResult(result)
    }

    // Si el login fue exitoso, navegar a la pantalla principal
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) onLoginSuccess()
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFADF0C7)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection {
            launcher.launch(viewModel.getSignInIntent())
        }
    }
}

@Composable
fun HeaderSection(onSignInClick: () -> Unit) {
    val img = painterResource(id = R.drawable.logo)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = img, contentDescription = "logo", modifier = Modifier.size(200.dp))

        Text(
            text = stringResource(R.string.eslogan),
            modifier = Modifier.padding(horizontal = 50.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        PrimaryButton(
            text = stringResource(R.string.btn_login),
            onClick = onSignInClick
        )
    }
}
