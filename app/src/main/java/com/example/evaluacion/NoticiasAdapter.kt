package com.example.evaluacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NoticiasAdapter(
    private val noticias: List<Noticia>,
    private val onItemClick: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = noticias[position]
        holder.bind(noticia)
        holder.itemView.setOnClickListener { onItemClick(noticia) }
    }

    override fun getItemCount(): Int = noticias.size

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        private val tvResumen: TextView = itemView.findViewById(R.id.tvResumen)
        private val ivNoticia: ImageView = itemView.findViewById(R.id.ivNoticia)

        fun bind(noticia: Noticia) {
            tvTitulo.text = noticia.titulo
            tvResumen.text = noticia.resumen
            
            if (noticia.imageUrl.isNotEmpty()) {
                ivNoticia.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(noticia.imageUrl)
                    .into(ivNoticia)
            } else {
                ivNoticia.visibility = View.GONE
            }
        }
    }
}