package org.example.form.network

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.example.form.asset.AssetDto

class ApiServices(private val client: HttpClient) {
    private val endPoint = "supplier"
    private val endPoint2 = "assets"
    private val testEndPoint = "post"
    private val registerEndPoint = "users/register"
    private val loginEndPoint = "users/login"


    suspend fun createSupplier(multipart: MultiPartFormDataContent): String {
        val response = client.post(endPoint) {
            setBody(multipart)
        }

        val body = response.bodyAsText()
        println("Response code: ${response.status}")
        println("Response body: $body")
        return body
    }

    suspend fun createAsset(multipart: MultiPartFormDataContent): String {
        val response = client.post(endPoint2) {
            setBody(multipart)
        }

        val body = response.bodyAsText()
        println("Response code: ${response.status}")
        println("Response body: $body")
        return body
    }

    suspend fun postAssetMultipart(dto: AssetDto) {
        val response = client.post(endPoint2) {
            setBody(MultiPartFormDataContent(formData {
                append("brand", dto.brand)
                append("group", dto.group)
                append("category", dto.category)
                append("name", dto.name)
                append("type", dto.type)
                append("alias", dto.alias)
                dto.photo_asset?.let {
                    append("photo_asset", it, Headers.build {
                        append("Content-Disposition", "filename=photo_asset.jpg")
                        append("Content-Type", "image/jpeg")
                    })
                }
            }))
        }

        val body = response.bodyAsText()
        println("Response code: ${response.status}")
        println("Response body: $body")
    }

    suspend fun testRegister() {
        val response = client.post(registerEndPoint) {
            setBody(
                buildJsonObject {
                    put("email", "testadmin@example.com")
                    put("role", "Admin")
                    put("password", "admin1234")
                    put("name", "Test User")
                }
            )
            contentType(ContentType.Application.Json)
        }

        val body = response.bodyAsText()
        println("Response code: ${response.status}")
        println("Response body: $body")
    }

    suspend fun testLogin() {
        val response = client.post(loginEndPoint) {
            setBody(
                buildJsonObject {
                    put("email", "testadmin@example.com")
                    put("password", "admin1234")
                }
            )
            contentType(ContentType.Application.Json)
        }

        val body = response.bodyAsText()
        println("Response code: ${response.status}")
        println("Response body: $body")

        try {
            val json = Json.parseToJsonElement(body).jsonObject
            val token = json["data"]?.jsonPrimitive?.content
            if (!token.isNullOrBlank()) {
                BearerToken.token = token
                println("Saved Bearer token: ${BearerToken.token}")
            } else {
                println("Login response did not contain a token.")
            }
        } catch (e: Exception) {
            println("Failed to parse login response: ${e.message}")
        }
    }
}
