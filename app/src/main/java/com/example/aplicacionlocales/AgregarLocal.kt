package com.example.aplicacionlocales

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI

class AgregarLocal : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var imagenBitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_local)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSeleccionar = findViewById<Button>(R.id.btnSeleccionarImagen)
        val imageView = findViewById<ImageView>(R.id.miImageView)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val nombreInput = findViewById<EditText>(R.id.nombreLocal)
        val precioInput = findViewById<EditText>(R.id.precioLocal)
        val direccionInput = findViewById<EditText>(R.id.direccionLocal)

        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,PICK_IMAGE_REQUEST)
        }

        btnGuardar.setOnClickListener {
            val nombre = nombreInput.text.toString()
            val precio = precioInput.text.toString().toDoubleOrNull() ?: 0.0
            val direccion = direccionInput.text.toString()

            val id = dbHelper.insertarLocales(nombre,precio,direccion,imagenBitmap,db)

            if (id > 0) {
                Toast.makeText(this,"Local guardado con ID $id", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val imageView = findViewById<ImageView>(R.id.miImageView)

            val inputStream = contentResolver.openInputStream(imageUri!!)
            imagenBitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(imagenBitmap)
        }
    }
}