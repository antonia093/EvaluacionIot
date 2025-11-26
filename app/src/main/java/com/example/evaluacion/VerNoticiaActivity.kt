package com.example.evaluacion

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerNoticiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_noticia)

        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val tvAutorFecha = findViewById<TextView>(R.id.tvAutorFecha)
        val tvContenido = findViewById<TextView>(R.id.tvContenidoDetalle)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val noticia = intent.getSerializableExtra("noticia") as? Noticia

        if (noticia != null) {
            tvTitulo.text = noticia.titulo
            tvAutorFecha.text = "Por: ${noticia.autor} | Fecha: ${noticia.fecha}"
            tvContenido.text = noticia.contenido
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}