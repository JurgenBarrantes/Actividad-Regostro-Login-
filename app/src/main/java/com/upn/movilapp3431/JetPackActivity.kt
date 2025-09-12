package com.upn.movilapp3431

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.upn.movilapp3431.composables.Contador
import com.upn.movilapp3431.entities.Contact
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme

class JetPackActivity : ComponentActivity() {

//    var counter = 0;
//    var counter = mutableStateOf(0) // .value
//    var counter by mutableIntStateOf(0)

//    var list by mutableStateOf(listOf<Contact>())
//    var s by mutableStateOf("")
//    var bol by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            MovilApp3431Theme {
//                var counter by remember {  mutableIntStateOf(0) }
//                var contact by remember {  mutableStateOf(Contact("1","Rafael Aguilar", "909090909", "2024-06-01T10:00:00Z")) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Contador()
                            Text("Hello World!", modifier = Modifier.padding(innerPadding) )
                        }
                    }


                }
            }
        }
    }
}

