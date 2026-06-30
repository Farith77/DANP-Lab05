package com.danp.lab5.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.danp.lab5.data.model.User

/**
 * Espejo del JSON de Django UserSerializer:
 * { id, name, username, email, profileImageUrl }
 *
 * No incluye 'password': el backend nunca lo devuelve por seguridad.
 */
data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("profileImageUrl") val profileImageUrl: String = ""
) {
    /**
     * Convierte a User.kt. El password queda vacío porque el backend
     * nunca lo envía; localmente no se necesita tras autenticarse.
     */
    fun toDomain(): User = User(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        profileImageUrl = profileImageUrl
    )
}