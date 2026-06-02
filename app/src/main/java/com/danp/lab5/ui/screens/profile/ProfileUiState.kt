package com.danp.lab5.ui.screens.profile

import com.danp.lab5.data.model.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false
)
