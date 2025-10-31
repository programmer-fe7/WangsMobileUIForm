package org.example.form.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.form.network.BearerToken.token

private const val baseUrl = "http://192.168.88.249:5050/v2/"         // Supply Learning Kit
const val baseUrl2 = "http://127.0.0.1:5000/v2/"                     // Asset Learning Kit
private const val testUrl = "https://postman-echo.com/"              // Postman Echo Test

object BearerToken {
    var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NjE5MDIzNDMsImlkIjoiT2JqZWN0SUQoXCI2OTAzMDkxODQyMTI2MjExNTUwZTdjNDlcIikiLCJyb2xlIjoidXNlciJ9.dYI00yKU5oRfKKFpZd9avJv4G4qShrLezr8Zmlj8Gn8"
    private const val nullToken = ""
}


fun createHttpClient(): HttpClient {
    return HttpClient {
        // https://api.ktor.io/ktor-client-core/io.ktor.client.plugins/-default-request/index.html
        defaultRequest {
            url(baseUrl2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 15_000
        }

        install(HttpCallValidator) {
            handleResponseException { cause, request ->
                null
            }
        }
    }
}
