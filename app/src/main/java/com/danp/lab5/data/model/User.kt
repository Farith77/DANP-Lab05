package com.danp.lab5.data.model

/**
 * Modelo de datos para el Usuario (SRP).
 */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val profileImageUrl: String = ""
)
