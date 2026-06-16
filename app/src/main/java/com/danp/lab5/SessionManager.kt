package com.danp.lab5

class SessionManager(private val logger: ProductLogger) {
    var currentUser: String? = null
        private set

    fun login(username: String) {
        currentUser = username
        logger.logVisit("session:login:$username")
    }

    fun logout() {
        currentUser = null
    }
}
