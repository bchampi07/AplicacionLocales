package com.example.aplicacionlocales

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegistroActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mAuth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnCrearCuenta)

        val switchAdmin = findViewById<Switch>(R.id.admin)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val password = etPassword.text.toString()
            if (nombre.isNotEmpty() && correo.isNotEmpty() && password.isNotEmpty()) {
                mAuth.createUserWithEmailAndPassword(correo, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val userId = mAuth.currentUser?.uid
                            val  userRef = database.getReference("usuarios").child(userId!!)
                            val userData = mapOf(
                                "name" to nombre,
                                "email" to correo,
                                "rol" to if(switchAdmin.isChecked) "admin" else "client"
                            )
                            userRef.setValue(userData).addOnCompleteListener { res ->
                                if(res.isSuccessful) {
                                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        } else {
                            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }


            }else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}