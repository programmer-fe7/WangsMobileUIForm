package org.example.form.network

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject

class SupplierApi(private val client: HttpClient) {

//    private val baseUrl = "http://192.168.88.249:5050/v2"
    private val baseUrl = "https://postman-echo.com"
//    private val endPoint = "/supplier"
    private val endPoint = "/post"
//    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NjEzMDA0MTQsImlkIjoiNjhlNDcyYzQ0NjRjODhiMzVhMGFhOTljIiwibmFtZSI6IlVzZXIiLCJyb2xlIjoidXNlciJ9.x8gqJwjcazOltRXibGdiTW8Wtd0HGkIN8gN8OVsplUw"
    private val token = ""

    suspend fun createSupplier(data: JsonObject): String {

        println("Request JSON: $data")

        val response: HttpResponse = client.post("$baseUrl$endPoint") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            contentType(ContentType.Application.Json)
            setBody(data) // send patched JsonObject
        }

        // existing response handling...
        val bodyText = response.bodyAsText()
        println("Response code: ${response.status}")

        return bodyText
    }
}
