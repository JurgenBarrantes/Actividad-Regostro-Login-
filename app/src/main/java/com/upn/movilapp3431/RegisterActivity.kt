package com.upn.movilapp3431

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme

class RegisterActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setContent {
            MovilApp3431Theme {
                RegisterScreen(
                    onRegisterSuccess = {
                        Toast.makeText(this, "Registro exitoso. Por favor, inicia sesión.", Toast.LENGTH_LONG).show()
                        finish()
                    },
                    auth = auth,
                    firestore = firestore
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var registrationError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña (mín. 6 caracteres)") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }

            registrationError?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        registrationError = "Todos los campos son obligatorios."
                        return@Button
                    }
                    if (password != confirmPassword) {
                        registrationError = "Las contraseñas no coinciden."
                        return@Button
                    }
                    if (password.length < 6) {
                         registrationError = "La contraseña debe tener al menos 6 caracteres."
                         return@Button
                    }

                    isLoading = true
                    registrationError = null

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser = task.result?.user
                                firebaseUser?.let {
                                    val userDocument = hashMapOf(
                                        "uid" to it.uid,
                                        "email" to it.email
                                    )
                                    firestore.collection("users").document(it.uid)
                                        .set(userDocument)
                                        .addOnSuccessListener {
                                            isLoading = false
                                            onRegisterSuccess()
                                        }
                                        .addOnFailureListener { e ->
                                            isLoading = false
                                            registrationError = "Error al guardar datos: ${e.message}"
                                            Log.w("RegisterActivity", "Error writing document to Firestore", e)
                                        }
                                }
                            } else {
                                isLoading = false
                                registrationError = "Error en el registro: ${task.exception?.localizedMessage ?: "Error desconocido"}"
                                Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                Text("Registrar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {
                (context as? ComponentActivity)?.finish()
            }) {
                Text("¿Ya tienes cuenta? Inicia Sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MovilApp3431Theme {
        val mockAuth = FirebaseAuth.getInstance()
        val mockFirestore = FirebaseFirestore.getInstance()
        RegisterScreen(onRegisterSuccess = {}, auth = mockAuth, firestore = mockFirestore )
    }
}
