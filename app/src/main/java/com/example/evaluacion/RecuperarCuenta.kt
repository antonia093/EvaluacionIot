package com.example.evaluacion

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion.databinding.ActivityRecuperarCuentaBinding

class RecuperarCuenta : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRecuperar.setOnClickListener {
            val email = binding.etEmail.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Ingresa tu email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingresa un email válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Instrucciones enviadas a $email", Toast.LENGTH_LONG).show()
            finish()
        }

        binding.tvVolverLogin.setOnClickListener {
            finish()
        }
    }
}