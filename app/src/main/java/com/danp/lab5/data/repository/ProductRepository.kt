package com.danp.lab5.data.repository

import com.danp.lab5.data.model.Product
import com.danp.lab5.data.remote.DjangoApiService

/**
 * Repositorio de productos. Ya no depende de ProductDataSource local,
 * toda la información viene de Django a través de DjangoApiService.
 */
class ProductRepository(
    private val apiService: DjangoApiService
) {

    /**
     * Obtiene productos desde Django, con filtros opcionales de búsqueda/categoría.
     * Retorna lista vacía si la petición falla (sin internet, servidor caído, etc.).
     */
    suspend fun getProducts(search: String? = null, category: String? = null): List<Product> {
        return try {
            val response = apiService.getProducts(search = search, category = category)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Obtiene el detalle de un producto específico por su ID.
     * Retorna null si no existe o si la petición falla.
     */
    suspend fun getProductById(productId: Int): Product? {
        return try {
            val response = apiService.getProductDetail(productId)
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}