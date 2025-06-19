package com.example.aplicacionlocales

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mAuth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.btnRegistrar)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor debe completar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Bienvenido $email", Toast.LENGTH_SHORT).show()
                        val userRef = database.getReference("usuarios")
                        val consulta = userRef.orderByChild("email").equalTo(email)
                        consulta.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    for (userSnap in snapshot.children) {
                                        val intent = Intent(this@MainActivity ,BuscarLocales::class.java)
                                        intent.putExtra("userEmail", userSnap.child("email").getValue(String::class.java))
                                        intent.putExtra("userRol", userSnap.child("rol").getValue(String::class.java))
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })


                    } else {
                        Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

        tvRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        var passwordVisible = false

        passwordInput.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (passwordInput.right - passwordInput.compoundDrawables[drawableEnd].bounds.width())) {
                    passwordVisible = !passwordVisible
                    if (passwordVisible) {
                        passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    } else {
                        passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    passwordInput.setSelection(passwordInput.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}