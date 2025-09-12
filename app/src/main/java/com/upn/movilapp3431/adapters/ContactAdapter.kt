package com.upn.movilapp3431.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.upn.movilapp3431.R
import com.upn.movilapp3431.adapters.ContactAdapter.ContactViewHolder
import com.upn.movilapp3431.entities.Contact

class ContactAdapter(val data: List<Contact>): Adapter<ContactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contacto, parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // esta funcion se comporta como un activity

        val view = holder.itemView
        val item = data[position]

        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val tvPhone = view.findViewById<TextView>(R.id.tvNumero)


        view.setOnClickListener {
            Toast.makeText(view.context, "Hola ${item.name}", Toast.LENGTH_SHORT).show()
        }

        tvNombre.text = "Mi nombre es: ${item.name}"
        tvPhone.text = "Mi telefono es: ${item.phone}"


    }

    override fun getItemCount(): Int {
        return data.size;
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}