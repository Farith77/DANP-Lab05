package com.danp.lab5.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.danp.lab5.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        val user = userRepository.getCurrentUser()
        _uiState.update { it.copy(user = user) }
    }

    fun logout() {
        userRepository.logout()
        _uiState.update { it.copy(user = null, isLoggedOut = true) }
    }
}
