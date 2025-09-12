package com.upn.movilapp3431

import com.upn.movilapp3431.entities.Contact
import com.upn.movilapp3431.entities.Peliculas

class LlenadoDeDatosPelis {

    fun getPeliculas(): List<Peliculas> {


//                val contact = Contact("2", "Juana Perez", "123456789", "2024-06-10")
//                val record = myRef.child("contacts").push()
//                contact.id = record.key.toString()
//                record.setValue(contact)
        return listOf(
            Peliculas("1", "Batman Regresa", "5"),
            Peliculas("2", "Superman", "3"),
            Peliculas("3", "Spiderman", "6"),
            Peliculas("4", "Los vengadores", "2"),
            Peliculas("5", "Thor", "7"),
            Peliculas("6", "Capitan America", "3"),
            Peliculas("7", "Iron Man", "4"),
            Peliculas("8", "Hulk", "2"),
            Peliculas("9", "Black Panther", "3")
        )
    }
}