package com.example.evaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AgregarNoticiaActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_noticia)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etResumen = findViewById<EditText>(R.id.etResumen)
        val etImageUrl = findViewById<EditText>(R.id.etImageUrl) // Nuevo campo
        val etContenido = findViewById<EditText>(R.id.etContenido)
        val etAutor = findViewById<EditText>(R.id.etAutor)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val resumen = etResumen.text.toString()
            val imageUrl = etImageUrl.text.toString() // Obtener URL
            val contenido = etContenido.text.toString()
            val autor = etAutor.text.toString()
            val fecha = etFecha.text.toString()

            if (titulo.isEmpty() || resumen.isEmpty() || contenido.isEmpty() || autor.isEmpty() || fecha.isEmpty()) {
                mostrarAlerta("Error", "Por favor, completa los campos obligatorios (la imagen es opcional).")
                return@setOnClickListener
            }

            val noticia = Noticia(
                titulo = titulo,
                resumen = resumen,
                contenido = contenido,
                autor = autor,
                fecha = fecha,
                imageUrl = imageUrl // Guardar URL
            )

            guardarNoticia(noticia)
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun guardarNoticia(noticia: Noticia) {
        db.collection("noticias")
            .add(noticia)
            .addOnSuccessListener {
                mostrarConfirmacion("Éxito", "Noticia guardada correctamente.")
            }
            .addOnFailureListener { e ->
                mostrarAlerta("Error", "No se pudo guardar la noticia: ${e.message}")
            }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun mostrarConfirmacion(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .show()
    }
}