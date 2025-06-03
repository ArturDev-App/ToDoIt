package com.example.todoit

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todoit.presentacion.navegation.NavigationHostController
import com.example.todoit.presentacion.screens.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isUserAuthenticated by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(true) }

            // Verificar estado de autenticación al iniciar
            LaunchedEffect(Unit) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val googleAccount = GoogleSignIn.getLastSignedInAccount(this@MainActivity)

                isUserAuthenticated = currentUser != null && googleAccount != null
                isLoading = false

                Log.d("Auth", "Usuario autenticado: $isUserAuthenticated")
            }

            when {
                isLoading -> {
                    LoadingScreen()
                }
                isUserAuthenticated -> {
                    NavigationHostController()
                }
                else -> {
                    LoginScreen(
                        onLoginSuccess = {
                            isUserAuthenticated = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}