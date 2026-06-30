package com.danp.lab5.data.remote

import com.danp.lab5.data.remote.dto.*
import retrofit2.http.*
import retrofit2.Response

/**
 * Define todas las rutas que consume la app, una por cada
 * endpoint expuesto en ecommerce_backend/.
 */
interface DjangoApiService {

    // ── Productos ───────────────────────────────────────────────
    // GET /api/products/?search=...
    @GET("api/products/")
    suspend fun getProducts(
        @Query("search") search: String? = null,
        @Query("category") category: String? = null
    ): Response<List<ProductDto>>

    // GET /api/products/<id>/
    @GET("api/products/{id}/")
    suspend fun getProductDetail(@Path("id") productId: Int): Response<ProductDto>

    // ── Usuarios / Autenticación ────────────────────────────────
    // POST /api/users/register/
    @POST("api/users/register/")
    suspend fun register(@Body request: RegisterRequestDto): Response<AuthResponseDto>

    // POST /api/users/login/
    @POST("api/users/login/")
    suspend fun login(@Body request: LoginRequestDto): Response<AuthResponseDto>

    // GET /api/users/profile/   (requiere token)
    @GET("api/users/profile/")
    suspend fun getProfile(): Response<UserDto>

    // ── Carrito ─────────────────────────────────────────────────
    // GET /api/cart/   (requiere token)
    @GET("api/cart/")
    suspend fun getCart(): Response<CartResponseDto>

    // POST /api/cart/   (requiere token)
    @POST("api/cart/")
    suspend fun addToCart(@Body request: AddToCartRequestDto): Response<CartItemDto>

    // DELETE /api/cart/<productId>/   (requiere token)
    @DELETE("api/cart/{productId}/")
    suspend fun removeFromCart(@Path("productId") productId: Int): Response<Unit>

    // ── Pedidos ─────────────────────────────────────────────────
    // GET /api/orders/   (requiere token)
    @GET("api/orders/")
    suspend fun getOrders(): Response<List<OrderDto>>

    // POST /api/orders/checkout/   (requiere token)
    @POST("api/orders/checkout/")
    suspend fun checkout(): Response<OrderDto>
}