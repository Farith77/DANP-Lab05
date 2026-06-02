package com.danp.lab5.ui.screens.register

import androidx.lifecycle.ViewModel
import com.danp.lab5.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, error = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun register() {
        val name = _uiState.value.name
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, completa todos los campos") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        
        val success = userRepository.register(name, email, password)
        
        if (success) {
            _uiState.update { it.copy(isLoading = false, isRegistered = true) }
        } else {
            _uiState.update { it.copy(isLoading = false, error = "Error al registrar el usuario") }
        }
    }
}
