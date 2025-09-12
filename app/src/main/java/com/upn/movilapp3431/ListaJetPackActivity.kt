package com.upn.movilapp3431

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.upn.movilapp3431.entities.Contact
import com.upn.movilapp3431.services.ApiService
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListaJetPackActivity : ComponentActivity() {


//    var contacts = mutableStateListOf<Contact>()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            // https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts
//        val contacts = listOf(
//            Contact("Juan Perez", "123456789"),
//            Contact("Maria Gomez", "987654321"),
//            Contact("Carlos Sanchez", "555555555"),
//            Contact("Ana Torres", "444444444"),
//            Contact("Luis Ramirez", "333333333"),
//            Contact("Sofia Fernandez", "222222222"),
//            Contact("Miguel Diaz", "111111111"),
//            Contact("Laura Morales", "666666666"),
//            Contact("Diego Castro", "777777777"),
//            Contact("Elena Rojas", "888888888"),
//            Contact("Pedro Vargas", "999999999"),
//            Contact("Marta Silva", "000000000"),
//            Contact("Jorge Herrera", "121212121"),
//            Contact("Lucia Mendoza", "343434343"),
//            Contact("Andres Fuentes", "565656565"),
//            Contact("Carmen Ortiz", "787878787"),
//            Contact("Rafael Aguilar", "909090909"),
//        )

        val retrofit = Retrofit.Builder()
            .baseUrl("https://68b5a32ae5dc090291afbd0d.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        setContent {
            MovilApp3431Theme {

                var contacts by remember { mutableStateOf(listOf<Contact>()) }
//                val contacts = remember { mutableStateListOf<Contact>() }
                var hasError by remember { mutableStateOf(false) }
                var isLoading by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    try {
                        hasError = false
                        isLoading = true
                        contacts  = apiService.getContacts()

                        Log.i("MAIN_APP", "Contacts: ${contacts.size}")
                    } catch (e: Exception) {
                        hasError = true
                        Log.e("MAIN_APP", "Error: ${e.message}")
                    } finally {
                        isLoading = false
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp)) {
                        if (isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            if (!hasError) {
                                Column {
                                    Button(
                                        onClick = {
                                           contacts += Contact("999", "Nuevo Contacto", "123123123", "2024-06-10T00:00:00.000Z")
                                            Log.i("MAIN_APP", "Contacts: ${contacts.size}")
                                        },
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Text(text = "Click")
                                    }
                                    LazyColumn {
                                    items(contacts) { contact ->
                                        val context = LocalContext.current
                                        Card(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 4.dp)
                                                .clickable {
                                                    Toast.makeText(
                                                        context,
                                                        "Seleccionó: ${contact.name}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                },
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                        ) {
                                            Column {
                                                Text(
                                                    text = contact.name,
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                                Text(
                                                    text = contact.phone,
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                }
                            } else {
                                Text(text = "Ocurrió un error al cargar los datos")
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovilApp3431Theme {
        Greeting("Android")
    }
}
 */