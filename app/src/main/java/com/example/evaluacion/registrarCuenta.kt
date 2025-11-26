package com.example.evaluacion

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion.databinding.ActivityRegistrarCuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrarCuenta : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarCuentaBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val TAG = "RegistrarCuenta"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmarPassword = binding.etConfirmarPassword.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmarPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear usuario en Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        
                        // Guardar datos adicionales en Firestore (opcional, pero buena práctica)
                        val userData = hashMapOf(
                            "nombre" to nombre,
                            "email" to email
                        )
                        
                        db.collection("usuarios").document(user!!.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "¡Cuenta creada exitosamente!", Toast.LENGTH_LONG).show()
                                finish() // Volver al login
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error writing document", e)
                                Toast.makeText(this, "Usuario creado, pero error al guardar datos.", Toast.LENGTH_LONG).show()
                                finish()
                            }
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Fallo al registrar: ${task.exception?.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.tvVolverLogin.setOnClickListener {
            finish()
        }
    }
}