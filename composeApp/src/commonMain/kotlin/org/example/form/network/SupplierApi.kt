package org.example.form.network

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import org.example.form.supplier.SupplierDto

class SupplierApi(private val client: HttpClient) {

    private val baseUrl = "http://192.168.88.249:5050/v2"
    // private val baseUrl = "https://postman-echo.com"
    private val endPoint = "/supplier"
    // private val endPoint = "/post"
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NjE3MTkwNjIsImlkIjoiNjhlNDcyYzQ0NjRjODhiMzVhMGFhOTljIiwibmFtZSI6IlVzZXIiLCJyb2xlIjoidXNlciJ9.zUAZ1R8oDPkpgWHZ1TpnpfprhwQUh6oxajyd84zDd6w"
    // private val token = ""

    suspend fun createSupplier(data: SupplierDto): String {
        // helper: guess basic image content type from header bytes
        fun guessImageContentType(bytes: ByteArray): ContentType {
            if (bytes.size >= 8 &&
                bytes[0] == 0x89.toByte() && bytes[1] == 0x50.toByte() && bytes[2] == 0x4E.toByte() &&
                bytes[3] == 0x47.toByte() && bytes[4] == 0x0D.toByte() && bytes[5] == 0x0A.toByte()
            ) {
                return ContentType.Image.PNG
            }
            // JPEG: 0xFF 0xD8
            if (bytes.size >= 2 && bytes[0] == 0xFF.toByte() && bytes[1] == 0xD8.toByte()) {
                return ContentType.Image.JPEG
            }
            // fallback
            return ContentType.Application.OctetStream
        }

        val multipart = MultiPartFormDataContent(formData {

            append("companyName", data.companyName)
            append("country", data.country ?: "")
            append("state", data.state ?: "")
            append("city", data.city ?: "")
            append("zipCode", data.zipCode ?: "")
            append("companyLocation", data.companyLocation ?: "")
            append("companyPhoneNumber", data.companyPhoneNumber ?: "")
            append("picName", data.picName ?: "")
            append("picPhoneNumber", data.picPhoneNumber ?: "")
            append("picEmail", data.picEmail ?: "")

            data.item?.forEachIndexed { i, item ->
                append("item[$i].itemName", item.itemName)

                item.sku.forEachIndexed { j, skuValue ->
                    append("item[$i].sku[$j]", skuValue)
                }
            }

            data.image?.let { bytes ->
                val filename = "upload-image" + when (guessImageContentType(bytes)) {
                    ContentType.Image.PNG -> ".png"
                    ContentType.Image.JPEG -> ".jpg"
                    else -> ".bin"
                }
                val contentType = guessImageContentType(bytes)

                // append as file with content-disposition and content-type headers
                append(
                    key = "image",
                    value = bytes,
                    headers = io.ktor.http.Headers.build {
                        append(
                            HttpHeaders.ContentDisposition,
                            "form-data; name=\"image\"; filename=\"$filename\""
                        )
                        append(HttpHeaders.ContentType, contentType.toString())
                    }
                )
            }
        })

        val response: HttpResponse = client.post("$baseUrl$endPoint") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            setBody(multipart)
        }

        val bodyText = response.bodyAsText()
        println("Response code: ${response.status}")
        println("Response body: $bodyText")
        return bodyText
    }
}
