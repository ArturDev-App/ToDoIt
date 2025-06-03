package com.example.todoit

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthManager(private val context: Context) {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.DEFAULT_WEB_CLIENT_ID) // Ahora debería funcionar correctamente
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
    private val auth = FirebaseAuth.getInstance()

    fun isUserSignedIn(): Boolean{
        val account = GoogleSignIn.getLastSignedInAccount(context)
        val firebaseUser = auth.currentUser
        return account != null && firebaseUser != null
    }

    fun signInWithGoogle(account: GoogleSignInAccount, callback: (Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthManager", "Autenticación con Firebase exitosa")
                    callback(true)
                } else {
                    Log.e("AuthManager", "Error en autenticación Firebase", task.exception)
                    callback(false)
                }
            }
    }
}