package com.danp.lab5.di

import com.danp.lab5.ProductLogger
import com.danp.lab5.SessionManager
import com.danp.lab5.data.remote.DjangoApiService
import com.danp.lab5.data.remote.RetrofitClient
import com.danp.lab5.data.repository.ProductRepository
import com.danp.lab5.data.repository.UserRepository
import com.danp.lab5.ui.screens.cart.CartViewModel
import com.danp.lab5.ui.screens.checkout.CheckoutViewModel
import com.danp.lab5.ui.screens.home.HomeViewModel
import com.danp.lab5.ui.screens.login.LoginViewModel
import com.danp.lab5.ui.screens.product_detail.ProductDetailViewModel
import com.danp.lab5.ui.screens.profile.ProfileViewModel
import com.danp.lab5.ui.screens.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Logger and Session
    singleOf(::ProductLogger)
    single { SessionManager(get()) }

    // Retrofit: una sola instancia de DjangoApiService para toda la app
    single<DjangoApiService> { RetrofitClient.apiService }

    // Repositories (ya no dependen de los DataSource locales)
    single { ProductRepository(get()) }
    single { UserRepository(get()) }

    // ViewModels
    single { CartViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProductDetailViewModel(get(), get()) }
    viewModel { CheckoutViewModel() }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
}