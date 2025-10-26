package org.example.form.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.wangsit.compose.wangs.ui.form.FormBuilder
import id.wangsit.compose.wangs.ui.form.Validator
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer
import org.example.form.network.SupplierApi
import org.example.form.network.createHttpClient

@Serializable
data class SupplierDto(
    val companyName: String,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null,
    val zipCode: String? = null,
    val companyLocation: String? = null,
    val companyPhoneNumber: String? = null,
    val item: List<SupplierItem>? = emptyList(),
    val picName: String? = null,
    val picPhoneNumber: String? = null,
    val picEmail: String? = null,
)

@Serializable
data class SupplierItem(
    val itemName: String,
    val sku: List<String>
)

private val client = createHttpClient()
private val api = SupplierApi(client)

suspend fun registerSupplier(body: JsonObject) {
    try {
        println("Submitting company form: $body")
        val result = api.createSupplier(body)
        println("✅ Response from server: $result")
    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }
}

/**
 * ViewModel that owns the Form instance.
 * Follows the same pattern as RegistrationViewModel in the sample.
 */
class SupplierFormViewModel : ViewModel() {
    val form = FormBuilder<SupplierDto>(
        coroutineScope = viewModelScope,
        serializer = serializer()
    ).apply {
        registerConverter(SupplierItem::class){ any ->
            any?.let { si ->
                buildJsonObject {
                    put("itemName", JsonPrimitive(si.itemName))
                    put("sku", buildJsonArray { si.sku.forEach { add(JsonPrimitive(it)) } })
                }
            }
        }

        // Required: companyName
        field(
            property = SupplierDto::companyName,
            validators = listOf(Validator.NotNullOrEmpty(), Validator.MaxLength(150))
        )

        // Country (optional but allowed to be required depending on your needs)
        field(
            property = SupplierDto::country,
            validators = listOf(Validator.NotNullOrEmpty()) // make required; remove validator if optional
        )

        // State (optional)
        field(
            property = SupplierDto::state,
            validators = listOf(Validator.MaxLength(100))
        )

        // City
        field(
            property = SupplierDto::city,
            validators = listOf(Validator.MaxLength(100))
        )

        // Company Address
        field(
            property = SupplierDto::companyLocation,
            validators = listOf(Validator.NotNullOrEmpty(), Validator.MaxLength(250))
        )

        // Zip code (Int)
        field(
            property = SupplierDto::zipCode,
            validators = listOf(Validator.NotNull(), Validator.PasswordPattern(
                Regex("[0-9]+")
            ))
        )

        // Company phone number
        field(
            property = SupplierDto::companyPhoneNumber,
            validators = listOf(Validator.NotNullOrEmpty(), Validator.MaxLength(30))
        )

        // PIC name
        field(
            property = SupplierDto::picName,
            validators = listOf(Validator.NotNullOrEmpty(), Validator.MaxLength(100))
        )

        // PIC phone
        field(
            property = SupplierDto::picPhoneNumber,
            validators = listOf(Validator.NotNullOrEmpty(), Validator.MaxLength(30))
        )

        // PIC email (validate format)
        field(
            property = SupplierDto::picEmail,
            validators = listOf(Validator.NotNullOrEmpty(), Validator.EmailFormat())
        )

        // Optional meta fields
        field(property = SupplierDto::item)

        // Submit handler (call your real repository here)
        onSubmitJsonObject { registerSupplier(it) }
    }.build()
}
