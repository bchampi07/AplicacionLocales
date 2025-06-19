package com.example.aplicacionlocales

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuscarLocales : AppCompatActivity() {

    private lateinit var recyclerLocales: RecyclerView
    private lateinit var localAdapter: LocalAdapter
    private val listaLocales = mutableListOf<Local>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_locales)

        recyclerLocales = findViewById(R.id.recyclerLocales)
        recyclerLocales.layoutManager = GridLayoutManager(this, 2)

        // Agregar locales

        listaLocales.add(Local("Local 1", "Arequipa", "S/.80", R.drawable.local1))
        listaLocales.add(Local("Local 2", "Arequipa", "S/.80", R.drawable.local2))
        listaLocales.add(Local("Local 3", "Arequipa", "S/.80", R.drawable.local1))
        listaLocales.add(Local("Local 4", "Arequipa", "S/.80", R.drawable.local1))
        listaLocales.add(Local("Local 5", "Arequipa", "S/.80", R.drawable.local1))


        localAdapter = LocalAdapter(listaLocales)
        recyclerLocales.adapter = localAdapter

        val btnCrearLocal = findViewById<Button>(R.id.crearLocal)
        val btnAgregarLocal = findViewById<Button>(R.id.crearLocal)

        btnAgregarLocal.setOnClickListener{
            val intent = Intent (this,AgregarLocal::class.java)
            startActivity(intent)
        }
        val userEmail = intent.getStringExtra("userEmail")
        val userRol = intent.getStringExtra("userRol")
          if (userRol != "admin") {
            btnCrearLocal.visibility = View.GONE
        }


    }
}
