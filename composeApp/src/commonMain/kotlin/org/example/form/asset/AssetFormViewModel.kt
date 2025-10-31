package org.example.form.asset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.wangsit.compose.wangs.ui.form.FormBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.example.form.network.ApiServices
import org.example.form.network.createHttpClient

@Serializable
data class AssetDto(
    val group: String,
    val brand: String,
    val name: String,
    val category: String,
    val alias: String,
    val type: String,
    val photo_asset: ByteArray? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AssetDto

        if (group != other.group) return false
        if (brand != other.brand) return false
        if (category != other.category) return false
        if (name != other.name) return false
        if (alias != other.alias) return false
        if (type != other.type) return false
        if (!photo_asset.contentEquals(other.photo_asset)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = group.hashCode()
        result = 31 * result + brand.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + alias.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (photo_asset?.contentHashCode() ?: 0)
        return result
    }
}

private val client = createHttpClient()
private val apiServices = ApiServices(client)

suspend fun registerAsset(body: MultiPartFormDataContent) {
    try {
        println("Login and get token:")
        apiServices.testRegister()
        delay(2000)
        apiServices.testLogin()

        println("Delaying for 1 second...")
        delay(1000)

        println("Submitting asset form: $body")
        val result = apiServices.createAsset(body)
        println("✅ Response from server: $result")
    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }
}

/**
 * ViewModel that owns the Form instance.
 * Follows the same pattern as RegistrationViewModel in the sample.
 */
class AssetFormViewModel : ViewModel() {
    val form = FormBuilder<AssetDto>(
        coroutineScope = viewModelScope,
        serializer = serializer()
    ).apply {

        field(property = AssetDto::group)

        field(property = AssetDto::brand)

        field(property = AssetDto::name)

        field(property = AssetDto::category)

        field(property = AssetDto::alias)

        field(property = AssetDto::type)

        field(property = AssetDto::photo_asset)

        // Submit handler (call your real repository here)
        onSubmitMultipartObject { registerAsset(it) }
    }.build()
}
