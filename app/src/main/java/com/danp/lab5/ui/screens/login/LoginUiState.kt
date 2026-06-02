package com.danp.lab5.ui.screens.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)
