package com.danp.lab5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danp.lab5.data.repository.ProductRepository
import com.danp.lab5.data.repository.UserRepository
import com.danp.lab5.ui.screens.home.HomeViewModel
import com.danp.lab5.ui.screens.cart.CartViewModel
import com.danp.lab5.ui.screens.login.LoginViewModel
import com.danp.lab5.ui.screens.product_detail.ProductDetailViewModel
import com.danp.lab5.ui.screens.checkout.CheckoutViewModel
import com.danp.lab5.ui.screens.profile.ProfileViewModel
import com.danp.lab5.ui.screens.register.RegisterViewModel

class ViewModelFactory(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(productRepository) as T
            modelClass.isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(productRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) ->
                ProductDetailViewModel(productRepository) as T
            modelClass.isAssignableFrom(CheckoutViewModel::class.java) ->
                CheckoutViewModel() as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(userRepository) as T
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}
