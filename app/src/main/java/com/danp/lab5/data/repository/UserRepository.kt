package com.danp.lab5.data.repository

import com.danp.lab5.data.model.User
import com.danp.lab5.data.remote.DjangoApiService
import com.danp.lab5.data.remote.RetrofitClient
import com.danp.lab5.data.remote.dto.LoginRequestDto
import com.danp.lab5.data.remote.dto.RegisterRequestDto

/**
 * Repositorio de usuarios. Ya no simula el login localmente:
 * llama a Django, guarda el token en RetrofitClient y cachea
 * el usuario actual en memoria.
 */
class UserRepository(
    private val apiService: DjangoApiService
) {
    private var currentUser: User? = null

    /**
     * Login real contra POST /api/users/login/.
     * Devuelve Result.success(User) o Result.failure con el motivo del error.
     */
    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val response = apiService.login(LoginRequestDto(username, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                RetrofitClient.authToken = authResponse.token
                val user = authResponse.user.toDomain()
                currentUser = user
                Result.success(user)
            } else {
                Result.failure(Exception("Usuario o contraseña incorrectos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Registro real contra POST /api/users/register/.
     * Crea el usuario en Django y deja la sesión iniciada automáticamente.
     */
    suspend fun register(name: String, username: String, email: String, password: String): Result<User> {
        return try {
            val response = apiService.register(
                RegisterRequestDto(
                    name = name,
                    username = username,
                    email = email,
                    password = password
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                RetrofitClient.authToken = authResponse.token
                val user = authResponse.user.toDomain()
                currentUser = user
                Result.success(user)
            } else {
                Result.failure(Exception("No se pudo completar el registro"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser(): User? = currentUser

    fun logout() {
        currentUser = null
        RetrofitClient.authToken = null
    }
}