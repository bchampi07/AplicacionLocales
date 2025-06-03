package com.example.aplicacionlocales

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class LocalAdapter(private val lista: List<Local>) : RecyclerView.Adapter<LocalAdapter.LocalViewHolder>() {
    inner class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imageLocal)
        val nombre: TextView = itemView.findViewById(R.id.nombreLocal)
        val ciudad: TextView = itemView.findViewById(R.id.detallesLocal)
        val precio: TextView = itemView.findViewById(R.id.precioLocal)
        val botonAgregar :ImageButton = itemView.findViewById(R.id.botonAgregar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_local,parent,false)
        return LocalViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        val local = lista[position]
        holder.imagen.setImageResource(local.imagenResId)
        holder.nombre.text = local.nombre
        holder.ciudad.text = local.ciudad
        holder.precio.text = local.precio

        holder.botonAgregar.setOnClickListener {

        }

    }

    override fun getItemCount(): Int = lista.size
}


