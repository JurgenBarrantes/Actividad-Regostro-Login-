package com.upn.movilapp3431

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upn.movilapp3431.adapters.ContactAdapter
import com.upn.movilapp3431.entities.Contact

class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val contacts = listOf(
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
            Contact("1","Rafael Aguilar", "909090909", "2024-06-01T10:00:00Z"),
        )

        val rvLista = findViewById<RecyclerView>(R.id.rvLista)
        rvLista.adapter = ContactAdapter(contacts)

        rvLista.layoutManager = LinearLayoutManager(this)
        rvLista.setHasFixedSize(true)

    }
}