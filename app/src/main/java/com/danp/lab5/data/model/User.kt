package com.danp.lab5.data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val profileImageUrl: String = ""
)
