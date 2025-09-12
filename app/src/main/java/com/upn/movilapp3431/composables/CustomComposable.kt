package com.upn.movilapp3431.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Contador() {
    Column {
        var counter by remember {  mutableIntStateOf(0) }

        Text(text = "$counter")
        Button(
            onClick = {
                counter++
                Log.i("MAIN_APP", "Counter: $counter")
            }
        ) {
            Text(text = "Click Me!")
        }
    }
}

