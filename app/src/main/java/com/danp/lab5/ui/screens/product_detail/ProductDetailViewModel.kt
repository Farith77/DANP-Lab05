package com.danp.lab5.ui.screens.product_detail

import androidx.lifecycle.ViewModel
import com.danp.lab5.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductDetailViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: Int, isInCart: Boolean) {
        _uiState.update { it.copy(isLoading = true) }
        val product = productRepository.getProductById(productId)
        _uiState.update { 
            it.copy(
                product = product,
                isInCart = isInCart,
                isLoading = false
            )
        }
    }

    fun onQuantityChange(newQuantity: Int) {
        if (newQuantity >= 1) {
            _uiState.update { it.copy(quantity = newQuantity) }
        }
    }

    fun setInCart(inCart: Boolean) {
        _uiState.update { it.copy(isInCart = inCart) }
    }
}
