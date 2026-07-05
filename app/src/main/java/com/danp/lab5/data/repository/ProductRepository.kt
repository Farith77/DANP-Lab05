package com.danp.lab5.data.repository

import com.danp.lab5.data.local.ProductDataSource
import com.danp.lab5.data.model.Product
import com.danp.lab5.data.remote.DjangoApiService

class ProductRepository(
    private val apiService: DjangoApiService,
    private val localDataSource: ProductDataSource  // ← recuperamos el DataSource local
) {

    suspend fun getProducts(search: String? = null, category: String? = null): List<Product> {
        return try {
            val response = apiService.getProducts(search = search, category = category)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: fallbackProducts(search, category)
            } else {
                fallbackProducts(search, category)
            }
        } catch (e: Exception) {
            // Sin conexión → usamos datos locales
            e.printStackTrace()
            fallbackProducts(search, category)
        }
    }

    suspend fun getProductById(productId: Int): Product? {
        return try {
            val response = apiService.getProductDetail(productId)
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else {
                localDataSource.getProductById(productId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            localDataSource.getProductById(productId)
        }
    }

    /**
     * Aplica los filtros de búsqueda sobre los datos locales,
     * imitando el comportamiento de la API cuando no hay conexión.
     */
    private fun fallbackProducts(search: String?, category: String?): List<Product> {
        val all = localDataSource.getAllProducts()
        return when {
            !search.isNullOrBlank() -> all.filter {
                it.name.contains(search, ignoreCase = true) ||
                        it.category.contains(search, ignoreCase = true)
            }
            !category.isNullOrBlank() -> all.filter {
                it.category.equals(category, ignoreCase = true)
            }
            else -> all
        }
    }
}