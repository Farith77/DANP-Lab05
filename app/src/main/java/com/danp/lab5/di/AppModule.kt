package com.danp.lab5.di

import com.danp.lab5.ProductLogger
import com.danp.lab5.SessionManager
import com.danp.lab5.data.local.ProductDataSource
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
import com.danp.lab5.data.local.UserDataSource

val appModule = module {
    // Logger and Session
    singleOf(::ProductLogger)
    single { SessionManager(get()) }

    // Retrofit
    single<DjangoApiService> { RetrofitClient.apiService }

    // Data Sources (local, para modo invitado)
    single { UserDataSource }   // ← agregar esta línea

    // Repositories
    single { ProductRepository(get(), ProductDataSource) }
    single { UserRepository(get()) }

    // ViewModels
    single { CartViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), UserDataSource) }
    viewModel { ProductDetailViewModel(get(), get()) }
    viewModel { CheckoutViewModel() }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
}