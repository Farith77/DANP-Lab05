package com.danp.lab5

import android.util.Log

class ProductLogger {
    private val visits = mutableMapOf<String, Int>()

    fun logVisit(productId: String) {
        val currentVisits = visits.getOrDefault(productId, 0)
        visits[productId] = currentVisits + 1
        Log.d("ProductLogger", "Visit logged for product: $productId. Total visits: ${visits[productId]}")
    }

    fun getMostVisited(): List<Pair<String, Int>> {
        return visits.toList().sortedByDescending { it.second }
    }
}
