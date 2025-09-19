package com.upn.movilapp3431

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
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme
import com.upn.movilapp3431.viewmodels.PeliculasListViewModel

class FirebasePelisRealtimeDatabaseActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovilApp3431Theme {
                val viewModel : PeliculasListViewModel by viewModels()

                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        ListaElementosP(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ListaElementosP(viewModel: PeliculasListViewModel) {
    if (viewModel.hasError) {
        Text(text = "Error al cargar los datos")
    } else {
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(viewModel.pelicula) { peliculas ->
                    Text(text = "${peliculas.nombre} - ${peliculas.episodios}")
                }
            }
        }
    }
}
