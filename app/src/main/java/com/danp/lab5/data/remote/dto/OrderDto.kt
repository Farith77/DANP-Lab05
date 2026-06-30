package com.danp.lab5.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Espejo de OrderItemSerializer de Django:
 * { id, product_name, unit_price, quantity }
 */
data class OrderItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("product_name") val productName: String,
    @SerializedName("unit_price") val unitPrice: Double,
    @SerializedName("quantity") val quantity: Int
)

/**
 * Espejo de OrderSerializer de Django:
 * { id, total, delivery_cost, status, created_at, items }
 *
 * Esta entidad no tiene equivalente en data/model porque las screens
 * de Checkout no requieren persistirla localmente; se consume directo
 * desde el repositorio para mostrar el historial de pedidos.
 */
data class OrderDto(
    @SerializedName("id") val id: Int,
    @SerializedName("total") val total: Double,
    @SerializedName("delivery_cost") val deliveryCost: Double,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("items") val items: List<OrderItemDto>
)