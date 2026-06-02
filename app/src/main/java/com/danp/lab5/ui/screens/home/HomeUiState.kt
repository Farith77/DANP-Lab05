package com.danp.lab5.ui.screens.home

import com.danp.lab5.data.model.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)
