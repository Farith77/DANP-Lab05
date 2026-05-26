package com.danp.lab5.data.model

/**
 * Modelo para un ítem en el carrito (SRP).
 */
data class CartItem(
    val product: Product,
    val quantity: Int
)
