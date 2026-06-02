package com.danp.lab5.data.local

import com.danp.lab5.data.model.User

object UserDataSource {
    fun getDefaultUser(): User = User(
        id = 1,
        name = "Jhamil Yeyder Turpo",
        username = "jhamil",
        email = "jhamil@example.com",
        password = "123",
        profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400"
    )
}
