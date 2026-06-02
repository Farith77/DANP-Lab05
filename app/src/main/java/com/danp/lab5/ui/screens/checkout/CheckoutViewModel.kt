package com.danp.lab5.ui.screens.checkout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckoutViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun confirmOrder(onConfirm: () -> Unit) {
        _uiState.update { it.copy(isLoading = true) }
        // Simulación de proceso de orden
        onConfirm()
        _uiState.update { it.copy(isLoading = false, orderConfirmed = true) }
    }
}
