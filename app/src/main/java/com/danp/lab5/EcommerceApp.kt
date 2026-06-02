package com.danp.lab5

import android.app.Application
import com.danp.lab5.data.local.ProductDataSource
import com.danp.lab5.data.local.UserDataSource
import com.danp.lab5.data.repository.ProductRepository
import com.danp.lab5.data.repository.UserRepository

class EcommerceApp : Application() {
    lateinit var productRepository: ProductRepository
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        productRepository = ProductRepository(ProductDataSource)
        userRepository = UserRepository(UserDataSource)
    }
}
