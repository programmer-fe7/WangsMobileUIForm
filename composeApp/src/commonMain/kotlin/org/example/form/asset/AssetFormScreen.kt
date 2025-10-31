package org.example.form.asset

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.wangsit.compose.wangs.ui.button.Button
import id.wangsit.compose.wangs.ui.form.FieldState
import id.wangsit.compose.wangs.ui.form.FormField
import id.wangsit.compose.wangs.ui.form.field.DropdownField
import id.wangsit.compose.wangs.ui.form.field.ImagePickerField
import id.wangsit.compose.wangs.ui.form.field.TextField
import id.wangsit.compose.wangs.ui.model.Option
import id.wangsit.compose.wangs.ui.theme.Spacing
import id.wangsit.compose.wangs.ui.util.Util.heightBox

@Composable
fun AssetFormScreen() {
    val viewModel = remember { AssetFormViewModel() }
    val form = remember { viewModel.form }

    val isFormSubmitting = form.isSubmitting
    val isFormValid = form.isValid
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Company Registration",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.Companion.padding(bottom = 20.dp)
        )

        FormField(form, AssetDto::group) {
            val options = listOf(
                Option("Group 1", "Group 1"),
                Option("Group 2", "Group 2"),
                Option("Group 3", "Group 3")
            )

            DropdownField(
                state = it as FieldState<String>,
                label = "Group",
                placeholder = "Select group",
                options = options,
            )
        }

        Spacing.m.adaptive.heightBox()

        FormField(form, AssetDto::brand) {
            val options = listOf(
                Option("Brand 1", "Brand 1"),
                Option("Brand 2", "Brand 2"),
                Option("Brand 3", "Brand 3")
            )

            DropdownField(
                state = it as FieldState<String>,
                label = "Brand",
                placeholder = "Select brand",
                options = options
            )
        }

        Spacing.m.adaptive.heightBox()

        FormField(form, AssetDto::name) {
            TextField(
                state = it,
                label = "Name",
                placeholder = "Enter name",
            )
        }

        Spacing.m.adaptive.heightBox()

        FormField(form, AssetDto::category) {
            val options = listOf(
                Option("Category 1", "Category 1"),
                Option("Category 2", "Category 2"),
                Option("Category 3", "Category 3")
            )

            DropdownField(
                state = it as FieldState<String>,
                label = "Category",
                placeholder = "Select category",
                options = options
            )
        }

        Spacing.m.adaptive.heightBox()

        FormField(form, AssetDto::alias) {
            TextField(
                state = it,
                label = "Alias",
                placeholder = "Enter alias",
            )
        }

        Spacing.m.adaptive.heightBox()

        FormField(form, AssetDto::type) {
            val options = listOf(
                Option("Type 1", "Type 1"),
                Option("Type 2", "Type 2"),
                Option("Type 3", "Type 3")
            )

            DropdownField(
                state = it as FieldState<String>,
                label = "Type",
                placeholder = "Select type",
                options = options
            )
        }

        Spacing.m.adaptive.heightBox()

        FormField(form, AssetDto::photo_asset) {
            ImagePickerField(
                state = it,
                label = "Photo",
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
