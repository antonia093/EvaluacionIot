package com.example.evaluacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PantallaInicio : AppCompatActivity() {

    private lateinit var rvNoticias: RecyclerView
    private lateinit var adapter: NoticiasAdapter
    private val noticiasList = mutableListOf<Noticia>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnAgregarNoticia = findViewById<FloatingActionButton>(R.id.btnAgregarNoticia)
        rvNoticias = findViewById(R.id.rvNoticias)

        // Configurar RecyclerView
        rvNoticias.layoutManager = LinearLayoutManager(this)
        adapter = NoticiasAdapter(noticiasList) { noticia ->
            // Al hacer clic en una noticia, ir a VerNoticia
            val intent = Intent(this, VerNoticiaActivity::class.java)
            intent.putExtra("noticia", noticia)
            startActivity(intent)
        }
        rvNoticias.adapter = adapter

        // Cargar noticias desde Firestore
        cargarNoticias()

        btnAgregarNoticia.setOnClickListener {
            startActivity(Intent(this, AgregarNoticiaActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun cargarNoticias() {
        db.collection("noticias")
            .orderBy("fecha", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("PantallaInicio", "Listen failed.", e)
                    Toast.makeText(this, "Error al cargar noticias", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                noticiasList.clear()
                for (doc in snapshots!!) {
                    val noticia = doc.toObject(Noticia::class.java)
                    noticia.id = doc.id
                    noticiasList.add(noticia)
                }
                adapter.notifyDataSetChanged()
            }
    }
}