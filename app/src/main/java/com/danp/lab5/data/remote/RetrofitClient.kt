package com.danp.lab5.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente único de Retrofit para toda la app.
 *
 * BASE_URL:
 * - Emulador Android  -> "http://10.0.2.2:8000/"
 * - Dispositivo físico -> "http://<IP_LOCAL_DE_TU_PC>:8000/"  (misma red WiFi)
 */
object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8000/"

    /** Token actual del usuario logueado. Se actualiza desde SessionManager al hacer login. */
    var authToken: String? = null

    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        authToken?.let { token ->
            requestBuilder.addHeader("Authorization", "Token $token")
        }
        chain.proceed(requestBuilder.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: DjangoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DjangoApiService::class.java)
    }
}