package com.danp.lab5.data.model

/**
 * Modelo de datos para un producto (SRP: Representa únicamente la entidad).
 */
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String = "",
    val category: String = ""
)
