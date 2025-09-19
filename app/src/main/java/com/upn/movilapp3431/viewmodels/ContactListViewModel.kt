package com.upn.movilapp3431.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.upn.movilapp3431.entities.Contact


class ContactListViewModel: ViewModel() {
    val contacts = mutableStateListOf<Contact>()
    var isLoading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

    private val database = Firebase.database
    private val contactsRef = database.getReference("contacts")

    init {
        loadContacts()
    }

    private fun loadContacts() {
        isLoading = true

        contactsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tempContacts = mutableListOf<Contact>()
                for (item in dataSnapshot.children) {
                    Log.d("MAIN_APP", "Value is: $item")
                    val contact = item.getValue(Contact::class.java)
                    tempContacts.add(contact!!)
                }

                contacts.addAll(tempContacts)
                isLoading = false

                Log.d("MAIN_APP", "Value is: ${contacts.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MAIN_APP", "Failed to read value.", error.toException())
                isLoading = false
                hasError = true
            }
        })
    }


}



/**
1. abrir aplicación
2. verificar si hay sesión iniciada en shared preferences
2.1 si hay sesión, ir a la pantalla principal
2.2 si no hay sesión, ir a la pantalla de login

2. primera en cargar es el Login ir a la pantalla principal (Firebase
3. Ingreso de usuario y contraseña
4. Validar usuario y contraseña contra firebase/backend
5. si es correcto, guardar en sesión (shared preferences el inicio de sesión)

 */