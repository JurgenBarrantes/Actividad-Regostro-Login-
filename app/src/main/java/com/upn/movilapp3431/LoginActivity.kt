package com.upn.movilapp3431

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton // Importado para el botón de registro
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.Firebase // Ya no se usa directamente aquí si usamos FirebaseAuth.getInstance()
// import com.google.firebase.auth.auth // KTX version, preferir getInstance()
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme
// Quitando importaciones de Realtime Database si no se usan directamente aquí para login
// import com.google.firebase.database.DataSnapshot
// import com.google.firebase.database.DatabaseError
// import com.google.firebase.database.ValueEventListener
// import com.google.firebase.database.database

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth // Declarar auth aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = FirebaseAuth.getInstance() // Inicializar auth

        val preferences = getSharedPreferences("com.upn.movilapp3431", MODE_PRIVATE)
        val estaLogueado = preferences.getBoolean("ESTA_LOGUEADO", false)

        // Código de prueba para crear usuario, considera moverlo a una lógica de registro o quitarlo
        // auth.createUserWithEmailAndPassword("miguel@gmail.com", "1234567")
        //     .addOnSuccessListener { task ->
        //         Log.i("LOGIN_APP", "Usuario de prueba creado: ${task.user?.uid}")
        //     }
        //     .addOnFailureListener { error ->
        //         Log.e("LOGIN_APP", "Error al crear usuario de prueba", error)
        //     }

        setContent {
            MovilApp3431Theme {
                val context = LocalContext.current

                if (estaLogueado) {
                    // Si ya está logueado, pasamos el rol guardado a la siguiente actividad
                    val rol = preferences.getString("ROL", "undefined")
                    val intent = Intent(context, FirebaseRealtimeDatabaseActivity::class.java).apply {
                        putExtra("USER_ROL", rol)
                    }
                    context.startActivity(intent)
                    finish()
                    return@MovilApp3431Theme
                }

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var isLoading by remember { mutableStateOf(false) }
                var loginError by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    val usernamePref = preferences.getString("EMAIL_LOGIN", null) // Usar una key diferente si es solo para login
                    if (usernamePref != null) {
                        email = usernamePref
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it.trim() },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            label = { Text("Contraseña") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                        if (isLoading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator()
                        }

                        loginError?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                if (email.isBlank() || password.isBlank()) {
                                    loginError = "Email y contraseña no pueden estar vacíos."
                                    return@Button
                                }
                                isLoading = true
                                loginError = null

                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        isLoading = false
                                        if (task.isSuccessful) {
                                            Log.i("LOGIN_APP", "Usuario logueado: ${task.result?.user?.uid}")
                                            val user = task.result?.user
                                            // Aquí asumimos que el rol se obtendrá después de un login exitoso,
                                            // quizás desde Firestore si no está en el token de Auth directamente.
                                            // Por ahora, simularemos un rol o lo dejaremos pendiente.
                                            // Para un ejemplo simple, guardamos solo el estado de logueo y email.
                                            val editor = preferences.edit()
                                            editor.putBoolean("ESTA_LOGUEADO", true)
                                            editor.putString("EMAIL_LOGIN", email) // Guardar email usado en login
                                            // editor.putString("ROL", "rol_obtenido_despues_del_login") // Obtener y guardar rol
                                            editor.apply()

                                            val intent = Intent(context, FirebaseRealtimeDatabaseActivity::class.java).apply {
                                                // putExtra("USER_ROL", "rol_obtenido_después_del_login")
                                            }
                                            context.startActivity(intent)
                                            finish()
                                        } else {
                                            loginError = "Error al iniciar sesión: ${task.exception?.localizedMessage ?: "Error desconocido"}"
                                            Log.w("LOGIN_APP", "signInWithEmail:failure", task.exception)
                                        }
                                    }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoading
                        ) {
                            Text("Ingresar")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        TextButton(onClick = {
                            val intent = Intent(context, RegisterActivity::class.java)
                            context.startActivity(intent)
                        }) {
                            Text("¿No tienes cuenta? Regístrate aquí")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    MovilApp3431Theme {
        var email by remember { mutableStateOf("test@example.com") }
        var password by remember { mutableStateOf("password") }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text("Email")}, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text("Contraseña")}, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(24.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Ingresar")
            }
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = {}) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}
