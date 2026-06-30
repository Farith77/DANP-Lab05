package com.danp.lab5.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.danp.lab5.data.model.Product

/**
 * Espejo del JSON que devuelve /api/products/ en Django.
 * Coincide con ProductSerializer: { id, name, price, description, imageUrl, category }
 */
data class ProductDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("category") val category: String = ""
) {
    /** Convierte el DTO de red al modelo de dominio que usa la UI. */
    fun toDomain(): Product = Product(
        id = id,
        name = name,
        price = price,
        description = description,
        imageUrl = imageUrl,
        category = category
    )
}