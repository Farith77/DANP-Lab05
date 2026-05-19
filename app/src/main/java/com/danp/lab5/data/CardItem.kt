package com.danp.lab5.data

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)