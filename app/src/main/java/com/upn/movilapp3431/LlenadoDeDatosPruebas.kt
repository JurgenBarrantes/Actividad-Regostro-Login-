package com.upn.movilapp3431

import com.upn.movilapp3431.entities.Contact

class LlenadoDeDatosPruebas {

    fun getContacts(): List<Contact> {

//                val contact = Contact("2", "Juana Perez", "123456789", "2024-06-10")
//                val record = myRef.child("contacts").push()
//                contact.id = record.key.toString()
//                record.setValue(contact)
        return listOf(
            Contact("1", "Juan Perez", "987654321", "2024-06-01"),
            Contact("2", "Juana Perez", "123456789", "2024-06-10"),
            Contact("3", "Carlos Sanchez", "456789123", "2024-06-15"),
            Contact("4", "Maria Lopez", "789123456", "2024-06-20"),
            Contact("5", "Ana Gomez", "321654987", "2024-06-25"),
            Contact("6", "Luis Torres", "654987321", "2024-06-30"),
            Contact("7", "Sofia Ramirez", "147258369", "2024-07-05"),
            Contact("8", "Miguel Fernandez", "369258147", "2024-07-10"),
            Contact("9", "Laura Martinez", "258369147", "2024-07-15"),
            Contact("10", "Diego Rodriguez", "159753486", "2024-07-20"),
        )
    }
}