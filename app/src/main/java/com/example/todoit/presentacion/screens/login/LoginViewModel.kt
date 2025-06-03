package com.example.todoit.presentacion.screens.login

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.example.todoit.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun getSignInIntent(): Intent {
        return authManager.googleSignInClient.signInIntent
    }

    fun handleSignInResult(result: ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            if (account != null) {
                Log.d("LoginViewModel", "Login exitoso: ${account.email}")

                authManager.signInWithGoogle(account) { success ->
                    if (success) {
                        Log.d("LoginViewModel", "Firebase Auth exitoso")
                        _loginSuccess.value = true
                    } else {
                        Log.e("LoginViewModel", "Error en Firebase Auth")
                        _loginSuccess.value = false
                    }

                }

            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Error al iniciar sesión: ${e.message}")
            _loginSuccess.value = false
        }
    }
}
