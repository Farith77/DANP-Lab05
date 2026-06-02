package com.danp.lab5.ui.screens.cart

import com.danp.lab5.data.model.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val isLoading: Boolean = false
)
