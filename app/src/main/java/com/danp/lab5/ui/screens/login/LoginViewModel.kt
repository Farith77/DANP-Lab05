package com.danp.lab5.ui.screens.login

import androidx.lifecycle.ViewModel
import com.danp.lab5.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun login() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, completa todos los campos") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        
        val success = userRepository.login(email, password)
        
        if (success) {
            _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
        } else {
            _uiState.update { it.copy(isLoading = false, error = "Credenciales incorrectas") }
        }
    }
}
