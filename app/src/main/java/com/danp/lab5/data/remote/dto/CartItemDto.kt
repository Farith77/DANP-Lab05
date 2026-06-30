package com.danp.lab5.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.danp.lab5.data.model.CartItem

/**
 * Espejo del JSON de Django CartItemSerializer: { product, quantity }
 * 'productId' es solo de ENTRADA al hacer POST /api/cart/, Django no lo devuelve.
 */
data class CartItemDto(
    @SerializedName("product") val product: ProductDto,
    @SerializedName("quantity") val quantity: Int
) {
    fun toDomain(): CartItem = CartItem(
        product = product.toDomain(),
        quantity = quantity
    )
}

/** Cuerpo enviado al agregar un producto al carrito. */
data class AddToCartRequestDto(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int
)

/** Respuesta completa de GET /api/cart/: { items: [...], total: 123.45 } */
data class CartResponseDto(
    @SerializedName("items") val items: List<CartItemDto>,
    @SerializedName("total") val total: Double
)