package com.example.evaluacion

import java.io.Serializable

data class Noticia(
    var id: String = "",
    val titulo: String = "",
    val resumen: String = "",
    val contenido: String = "",
    val autor: String = "",
    val fecha: String = ""
) : Serializable