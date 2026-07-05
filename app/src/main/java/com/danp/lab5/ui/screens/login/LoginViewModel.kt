package com.danp.lab5.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp.lab5.SessionManager
import com.danp.lab5.data.local.UserDataSource
import com.danp.lab5.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val userDataSource: UserDataSource  // ← fuente local para invitado
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun login() {
        val username = _uiState.value.email
        val password = _uiState.value.password

        if (username.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, completa todos los campos") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            userRepository.login(username, password)
                .onSuccess { user ->
                    sessionManager.login(user.username)
                    _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Credenciales incorrectas"
                        )
                    }
                }
        }
    }

    /**
     * Ingresa sin conexión usando el perfil local de UserDataSource.
     * No llama a la API ni guarda token — solo carga el usuario por defecto.
     */
    fun loginAsGuest() {
        val guestUser = userDataSource.getDefaultUser()
        sessionManager.login(guestUser.username)
        _uiState.update { it.copy(isLoggedIn = true) }
    }
}