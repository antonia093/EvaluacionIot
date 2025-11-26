package com.example.evaluacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        auth = FirebaseAuth.getInstance()
        
        // Verificar si el usuario ya está logueado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            irAPantallaInicio(currentUser.email ?: "")
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegistrar = findViewById<TextView>(R.id.tvRegistrar)
        val tvRecuperar = findViewById<TextView>(R.id.tvRecuperar)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_SHORT).show()
                        irAPantallaInicio(user?.email ?: email)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Autenticación fallida.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        tvRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistrarCuenta::class.java))
        }

        tvRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuperarCuenta::class.java))
        }
    }

    private fun irAPantallaInicio(email: String) {
        val intent = Intent(this, PantallaInicio::class.java)
        intent.putExtra("EMAIL", email)
        startActivity(intent)
        finish()
    }
}