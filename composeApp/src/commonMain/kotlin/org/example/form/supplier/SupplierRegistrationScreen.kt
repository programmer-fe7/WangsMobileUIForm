package org.example.form.supplier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import id.wangsit.compose.wangs.ui.button.Button
import id.wangsit.compose.wangs.ui.form.FormField
import id.wangsit.compose.wangs.ui.form.Validator
import id.wangsit.compose.wangs.ui.form.field.DropdownField
import id.wangsit.compose.wangs.ui.form.field.FieldLabel
import id.wangsit.compose.wangs.ui.form.field.MultiDropdownField
import id.wangsit.compose.wangs.ui.form.field.TextField
import id.wangsit.compose.wangs.ui.form.field.phonenumberfield.PhoneNumberField
import id.wangsit.compose.wangs.ui.model.Option
import id.wangsit.compose.wangs.ui.theme.Spacing
import id.wangsit.compose.wangs.ui.util.Util.heightBox

@Composable
fun SupplierRegistrationScreen() {
    val viewModel = remember { SupplierFormViewModel() }
    val form = remember { viewModel.form }

    val isFormSubmitting = form.isSubmitting
    val isFormValid = form.isValid
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Company Registration",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Company Name (required)
        FormField(form, SupplierDto::companyName) { state ->
            TextField(
                state = state,
                label = "Company Name",
                placeholder = "Enter company name",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // Country (simple dropdown example)
        FormField(form, SupplierDto::country) { state ->
            DropdownField(
                state = state,
                label = "Country",
                placeholder = "Select country",
                options = listOf("Indonesia", "Singapore", "Malaysia", "United States").map { Option(it, it) },
                showSearchBar = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // State
        FormField(form, SupplierDto::state) { state ->
            DropdownField(
                state = state,
                label = "State",
                placeholder = "Select state",
                options = listOf("Select state", "State A", "State B").map { Option(it, it) },
                showSearchBar = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        FormField(form, SupplierDto::city) { state ->
            DropdownField(
                state = state,
                label = "City",
                placeholder = "Select city",
                options = listOf("Select city", "City A", "City B").map { Option(it, it) },
                showSearchBar = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        FormField(form, SupplierDto::item) { state ->
            FormField(form, SupplierDto::item) { state ->
                val options = listOf(
                    Option("Item 1",
                        SupplierItem("Item 1", listOf("SKU 1", "SKU 2"))
                    ),
                    Option("Item 2",
                        SupplierItem("Item 2", listOf("SKU 3", "SKU 4"))
                    ),
                    Option("Item 3",
                        SupplierItem("Item 3", listOf("SKU 5", "SKU 6"))
                    )
                )

                MultiDropdownField(
                    state = state,
                    options = options,
                    label = "Item",
                    placeholder = "Select item",
                    showSearchBar = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacing.md.adaptive.heightBox()

        // Company Address (companyLocation)
        FormField(form, SupplierDto::companyLocation) { state ->
            TextField(
                state = state,
                label = "Company Address",
                placeholder = "Enter company address",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // ZIP code (number)
        FormField(form, SupplierDto::zipCode) { state ->
            val required = remember {
                state.validators.firstOrNull {
                    it is Validator.NotNull || it is Validator.NotNullOrEmpty
                } != null
            }

            val labelComposable = remember { @Composable { FieldLabel(text = "ZIP Code", required = required) } }
            val placeholderComposable = remember {
                @Composable { Text(text = "Enter zip code", modifier = Modifier.fillMaxWidth()) }
            }

            TextField(
                state = state,
                label = labelComposable,
                placeholder = placeholderComposable,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // Company Phone
        FormField(form, SupplierDto::companyPhoneNumber) { state ->
            PhoneNumberField(
                state = state,
                label = "Company Phone Number",
                placeholder = "Enter company phone number",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // PIC Name
        FormField(form, SupplierDto::picName) { state ->
            TextField(
                state = state,
                label = "PIC Name",
                placeholder = "Enter PIC name",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // PIC Phone
        FormField(form, SupplierDto::picPhoneNumber) { state ->
            PhoneNumberField(
                state = state,
                label = "PIC Phone Number",
                placeholder = "Enter PIC phone number",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacing.md.adaptive.heightBox()

        // PIC Email
        FormField(form, SupplierDto::picEmail) { state ->
            TextField(
                state = state,
                label = "PIC Email",
                placeholder = "Enter PIC email",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = { form.submit() },
            enabled = !isFormSubmitting && isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isFormSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submitting...")
            } else {
                Text("Submit")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { form.reset() },
            enabled = !isFormSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset")
        }
    }
}
