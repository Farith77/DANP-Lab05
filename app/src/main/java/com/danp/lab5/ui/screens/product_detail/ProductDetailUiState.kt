package com.danp.lab5.ui.screens.product_detail

import com.danp.lab5.data.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val quantity: Int = 1,
    val isInCart: Boolean = false,
    val isLoading: Boolean = false
)
