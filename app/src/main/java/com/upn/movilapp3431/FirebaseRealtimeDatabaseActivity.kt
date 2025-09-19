package com.upn.movilapp3431

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme
import com.upn.movilapp3431.viewmodels.ContactListViewModel

class FirebaseRealtimeDatabaseActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = this;
        enableEdgeToEdge()
        setContent {
            MovilApp3431Theme {
                val context = LocalContext.current
                val viewModel : ContactListViewModel by viewModels()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        val preferences = getSharedPreferences("com.upn.movilapp3431", MODE_PRIVATE)
                        var userRole = preferences.getString("ROLE",  "")

                        if (userRole == "admin") {
                            Column {
                                LogOut(preferences, activity)
                                Text("Esto es el rol de Admin")
                            }
                        } else

                            Column {
                                LogOut(preferences, activity)
                                ListaElementos(viewModel)
                            }

                    }
                }
            }
        }
    }
}

@Composable
fun LogOut(preferences: SharedPreferences, activity: FirebaseRealtimeDatabaseActivity) {
    val context = LocalContext.current

    Button(onClick = {

        preferences.edit().putBoolean("ESTA_LOGUEADO", false).apply()
        activity.finish()
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)

    }) {
        Text(text = "Cerrar Sesión")
    }
}

@Composable
fun ListaElementos(viewModel : ContactListViewModel) {
    if (viewModel.hasError) {
        Text(text = "Error al cargar los datos")
    } else {
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(viewModel.contacts) { contact ->
                    Text(text = "${contact.name} - ${contact.phone}")
                }
            }
        }
    }
}