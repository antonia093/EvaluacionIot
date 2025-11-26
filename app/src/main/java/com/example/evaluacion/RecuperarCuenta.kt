package com.example.evaluacion

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion.databinding.ActivityRecuperarCuentaBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class RecuperarCuenta : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarCuentaBinding
    private val db = FirebaseFirestore.getInstance()

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

            // Validar si el usuario existe en Firestore (colección 'usuarios')
            // Nota: Esto requiere que al registrarse se guarde el usuario en Firestore también.
            db.collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // El usuario existe
                        val nuevaPassword = generarNuevaPassword()
                        mostrarNuevaPassword(nuevaPassword)
                        
                        // NOTA: En un entorno real con Firebase Auth, NO se puede cambiar la contraseña 
                        // de un usuario arbitrario desde el cliente sin estar logueado o usar el Admin SDK.
                        // Esta implementación cumple con el requerimiento visual de "mostrar nueva contraseña",
                        // pero NO actualiza la contraseña real de Firebase Auth por limitaciones de seguridad del SDK.
                    } else {
                        Toast.makeText(this, "El correo no está registrado", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al verificar el usuario", Toast.LENGTH_SHORT).show()
                }
        }

        binding.tvVolverLogin.setOnClickListener {
            finish()
        }
    }

    private fun generarNuevaPassword(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    private fun mostrarNuevaPassword(password: String) {
        AlertDialog.Builder(this)
            .setTitle("Recuperación Exitosa")
            .setMessage("Tu nueva contraseña temporal es:\n\n$password\n\n(Nota: Esto es una simulación académica, la contraseña real de Auth no ha cambiado)")
            .setPositiveButton("Aceptar") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}