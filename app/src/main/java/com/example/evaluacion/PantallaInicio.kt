package com.example.evaluacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PantallaInicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        // Recibir email del login
        val email = intent.getStringExtra("EMAIL") ?: "Usuario"
        tvBienvenida.text = "¡Bienvenido, $email!"

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}