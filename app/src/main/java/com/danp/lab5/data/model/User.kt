package com.danp.lab5.data.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val password: String = "",
    val profileImageUrl: String = ""
)
