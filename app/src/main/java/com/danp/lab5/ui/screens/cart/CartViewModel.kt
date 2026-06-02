package com.danp.lab5.ui.screens.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.data.model.Product
import com.danp.lab5.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun addToCart(product: Product, quantity: Int = 1) {
        val index = _cartItems.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            _cartItems[index] = _cartItems[index].copy(
                quantity = _cartItems[index].quantity + quantity
            )
        } else {
            _cartItems.add(CartItem(product = product, quantity = quantity))
        }
        updateUiState()
    }

    fun increaseQuantity(item: CartItem) {
        val index = _cartItems.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            _cartItems[index] = _cartItems[index].copy(quantity = _cartItems[index].quantity + 1)
        }
        updateUiState()
    }

    fun decreaseQuantity(item: CartItem) {
        val index = _cartItems.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            if (_cartItems[index].quantity > 1) {
                _cartItems[index] = _cartItems[index].copy(quantity = _cartItems[index].quantity - 1)
            } else {
                _cartItems.removeAt(index)
            }
        }
        updateUiState()
    }

    fun removeItem(item: CartItem) {
        _cartItems.removeAll { it.product.id == item.product.id }
        updateUiState()
    }

    fun clearCart() {
        _cartItems.clear()
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.update { it.copy(cartItems = _cartItems.toList()) }
    }
}
