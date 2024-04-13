package com.stu74536.lab3_74536

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

/*
@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    val authState by viewModel.auth.collectAsState()

    when (authState) {
        is authState.Authenticated -> {
            // Utilisateur connecté, affichez l'écran principal
        }
        is authState.Unauthenticated -> {
            // Utilisateur non connecté, affichez l'écran d'authentification
            AuthForm(onLoginClick = { viewModel.login() }, onRegisterClick = { viewModel.register() })
        }
    }
}
*/
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
}
class AuthViewModel : ViewModel() {
    val auth = Firebase.auth
    //val authState: StateFlow<AuthState> = auth.authStateFlow

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}