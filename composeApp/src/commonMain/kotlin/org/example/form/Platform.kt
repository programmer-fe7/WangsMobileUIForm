package org.example.form

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform