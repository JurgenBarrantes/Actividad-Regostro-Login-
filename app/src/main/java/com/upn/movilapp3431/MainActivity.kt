package com.upn.movilapp3431

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.i("MAIN_APP", "Iniciando actividad MainActivity")

        val btnCounter = findViewById<Button>(R.id.btnCounter)
        val tvCounter = findViewById<TextView>(R.id.tvCounter)

        btnCounter.setOnClickListener {
            Log.i("MAIN_APP", "Click en boton")
            counter++;
            tvCounter.text = "$counter"
        }
    }
}