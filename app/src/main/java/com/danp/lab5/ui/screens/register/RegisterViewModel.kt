package com.danp.lab5.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp.lab5.SessionManager
import com.danp.lab5.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

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

        // Django requiere 'username' explícito; lo derivamos del email igual que antes
        val username = email.substringBefore("@")

        viewModelScope.launch {
            userRepository.register(name = name, username = username, email = email, password = password)
                .onSuccess { user ->
                    sessionManager.login(user.username)
                    _uiState.update { it.copy(isLoading = false, isRegistered = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al registrar el usuario"
                        )
                    }
                }
        }
    }
}