package com.example.evaluacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "Activity creada") // Debug

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegistrar = findViewById<TextView>(R.id.tvRegistrar)
        val tvRecuperar = findViewById<TextView>(R.id.tvRecuperar)

        Log.d(TAG, "Vistas encontradas: ${etEmail != null}") // Debug

        btnLogin.setOnClickListener {
            Log.d(TAG, "Botón login presionado") // Debug

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            Log.d(TAG, "Email: $email, Password: $password") // Debug

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Campos vacíos") // Debug
                return@setOnClickListener
            }

            // Credenciales de prueba - MÁS FÁCILES
            val loginExitoso = when {
                email == "user@test.com" && password == "123" -> true
                email == "test@test.com" && password == "test" -> true
                email == "admin" && password == "admin" -> true
                email.contains("@") && password == "123456" -> true // Cualquier email con esta pass
                else -> false
            }

            if (loginExitoso) {
                Log.d(TAG, "Login EXITOSO") // Debug
                Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PantallaInicio::class.java)
                intent.putExtra("EMAIL", email)
                startActivity(intent)
                finish()
            } else {
                Log.d(TAG, "Login FALLIDO") // Debug
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                // Muestra las credenciales esperadas en el Toast
                Toast.makeText(this, "Prueba: user@test.com / 123", Toast.LENGTH_LONG).show()
            }
        }

        tvRegistrar.setOnClickListener {
            Log.d(TAG, "Ir a registrar cuenta") // Debug
            startActivity(Intent(this, RegistrarCuenta::class.java))
        }

        tvRecuperar.setOnClickListener {
            Log.d(TAG, "Ir a recuperar cuenta") // Debug
            startActivity(Intent(this, RecuperarCuenta::class.java))
        }
    }
}