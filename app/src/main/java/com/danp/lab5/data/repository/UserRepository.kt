package com.danp.lab5.data.repository

import com.danp.lab5.data.local.UserDataSource
import com.danp.lab5.data.model.User

class UserRepository(private val dataSource: UserDataSource) {
    private var currentUser: User? = null

    fun login(emailOrUsername: String, password: String): Boolean {
        // Simulación de login: si es "jhamil", cargamos el usuario por defecto del DataSource
        val defaultUser = dataSource.getDefaultUser()
        
        currentUser = if (emailOrUsername == defaultUser.username || emailOrUsername == defaultUser.email) {
            defaultUser
        } else {
            User(
                id = (100..999).random(),
                name = "Usuario Invitado",
                username = emailOrUsername,
                email = if (emailOrUsername.contains("@")) emailOrUsername else "$emailOrUsername@example.com",
                password = password,
                profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400"
            )
        }
        return true
    }

    fun register(name: String, email: String, password: String): Boolean {
        currentUser = User(
            id = (100..999).random(),
            name = name,
            username = email.split("@")[0],
            email = email,
            password = password,
            profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400"
        )
        return true
    }

    fun getCurrentUser(): User? = currentUser

    fun logout() {
        currentUser = null
    }
}
