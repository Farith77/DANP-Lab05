package com.danp.lab5.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp.lab5.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _uiState.update { it.copy(isLoading = true) }

        // getProducts() es suspend -> debe llamarse dentro de una corrutina
        viewModelScope.launch {
            val products = productRepository.getProducts()
            _uiState.update {
                it.copy(
                    products = products,
                    isLoading = false
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun getFilteredProducts() = uiState.value.let { state ->
        if (state.searchQuery.isBlank()) {
            state.products
        } else {
            state.products.filter {
                it.name.contains(state.searchQuery, ignoreCase = true) ||
                        it.category.contains(state.searchQuery, ignoreCase = true)
            }
        }
    }
}