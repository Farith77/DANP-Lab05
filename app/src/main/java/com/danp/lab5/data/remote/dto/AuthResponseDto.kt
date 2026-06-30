package com.danp.lab5.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Espejo de la respuesta de /api/users/login/ y /api/users/register/:
 * { "user": {...}, "token": "abc123" }
 */
data class AuthResponseDto(
    @SerializedName("user") val user: UserDto,
    @SerializedName("token") val token: String
)

/**
 * Cuerpo enviado al hacer login.
 * Coincide con LoginSerializer de Django: { username, password }
 */
data class LoginRequestDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

/**
 * Cuerpo enviado al registrarse.
 * Coincide con RegisterSerializer de Django:
 * { name, username, email, password, profileImageUrl }
 */
data class RegisterRequestDto(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("profileImageUrl") val profileImageUrl: String = ""
)