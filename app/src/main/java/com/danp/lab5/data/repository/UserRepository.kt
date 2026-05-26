package com.danp.lab5.data.repository

import com.danp.lab5.data.model.User

object UserRepository {
    private val defaultUser = User(
        id = 1,
        name = "Jhamil Yeyder Turpo",
        username = "jhamil",
        email = "jhamil@example.com",
        password = "123",
        profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400"
    )

    private var currentUser: User? = null

    init {
        // Por defecto, nadie está logueado al inicio, o podemos poner al defaultUser si queremos que ya aparezca.
        // Pero el flujo pide Login, así que lo dejamos en null.
    }

    fun login(emailOrUsername: String, password: String): Boolean {
        // Simulación de login exitoso con cualquier dato
        currentUser = User(
            id = 1,
            name = if (emailOrUsername == "jhamil") "Jhamil Yeyder Turpo" else "Usuario Invitado",
            username = emailOrUsername,
            email = if (emailOrUsername.contains("@")) emailOrUsername else "$emailOrUsername@example.com",
            password = password,
            profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400"
        )
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
