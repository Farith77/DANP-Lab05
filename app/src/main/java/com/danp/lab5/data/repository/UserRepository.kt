package com.danp.lab5.data.repository

import com.danp.lab5.data.model.User

object UserRepository {
    private var currentUser: User? = null

    fun login(email: String, password: String): Boolean {
        // Simulación de login exitoso
        currentUser = User(
            id = 101,
            name = "Juan Pérez",
            email = email,
            profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400"
        )
        return true
    }

    fun register(name: String, email: String, password: String): Boolean {
        // Simulación de registro
        currentUser = User(
            id = 102,
            name = name,
            email = email
        )
        return true
    }

    fun getCurrentUser(): User? = currentUser

    fun logout() {
        currentUser = null
    }
}
