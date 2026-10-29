package com.example.kulinernav.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kulinernav.data.Place
import com.example.kulinernav.ui.theme.KulinerNavTheme

/**
 * Form tambah/edit tempat kuliner.
 * Validasi: nama wajib diisi (min 3 huruf), kategori & lokasi wajib, rating 1.0 - 5.0.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceFormScreen(
    initialPlace: Place?,
    isEditMode: Boolean,
    onSave: (name: String, category: String, location: String, rating: Double, description: String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val startName = initialPlace?.name.orEmpty()
    val startCategory = initialPlace?.category.orEmpty()
    val startLocation = initialPlace?.location.orEmpty()
    val startRating = initialPlace?.rating?.toString().orEmpty()
    val startDescription = initialPlace?.description.orEmpty()

    var name by rememberSaveable { mutableStateOf(startName) }
    var category by rememberSaveable { mutableStateOf(startCategory) }
    var location by rememberSaveable { mutableStateOf(startLocation) }
    var ratingText by rememberSaveable { mutableStateOf(startRating) }
    var description by rememberSaveable { mutableStateOf(startDescription) }
    var submitted by rememberSaveable { mutableStateOf(false) }

    // Validasi
    val nameError = when {
        name.isBlank() -> "Nama tempat wajib diisi"
        name.trim().length < 3 -> "Nama minimal 3 karakter"
        else -> null
    }
    val categoryError = if (category.isBlank()) "Kategori wajib diisi" else null
    val locationError = if (location.isBlank()) "Lokasi wajib diisi" else null
    val ratingValue = ratingText.replace(',', '.').toDoubleOrNull()
    val ratingError = when {
        ratingText.isBlank() -> "Rating wajib diisi"
        ratingValue == null -> "Rating harus berupa angka"
        ratingValue !in 1.0..5.0 -> "Rating harus antara 1.0 dan 5.0"
        else -> null
    }
    val descriptionError = if (description.length > 200) "Deskripsi maksimal 200 karakter" else null
    val isValid = listOf(nameError, categoryError, locationError, ratingError).all { it == null }

    fun resetForm() {
        name = startName
        category = startCategory
        location = startLocation
        ratingText = startRating
        description = startDescription
        submitted = false
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Tempat" else "Tambah Tempat") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Filled.Close, contentDescription = "Batal")
                    }
                },
                actions = {
                    TextButton(onClick = ::resetForm) { Text("Reset") }
                }
            )
        }
    ) { innerPadding ->
        if (isEditMode && initialPlace == null) {
            // Edit dengan ID invalid
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Data yang ingin diedit tidak ditemukan.", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(16.dp))
                OutlinedButton(onClick = onCancel) { Text("Kembali") }
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormField(
                value = name, onValueChange = { name = it },
                label = "Nama Tempat *", error = nameError, showError = submitted
            )
            FormField(
                value = category, onValueChange = { category = it },
                label = "Kategori * (mis. Sate, Soto)", error = categoryError, showError = submitted
            )
            FormField(
                value = location, onValueChange = { location = it },
                label = "Lokasi / Kota *", error = locationError, showError = submitted
            )
            FormField(
                value = ratingText, onValueChange = { ratingText = it },
                label = "Rating (1.0 - 5.0) *", error = ratingError, showError = submitted,
                keyboardType = KeyboardType.Decimal
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi (opsional)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) {
                    Text("Batal")
                }
                Button(
                    onClick = {
                        submitted = true
                        if (isValid) {
                            onSave(
                                name.trim(), category.trim(), location.trim(),
                                ratingValue ?: 0.0, description.trim()
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Simpan")
                }
            }
        }
    }
}

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    showError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val isError = showError && error != null
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        supportingText = { if (isError) Text(error!!) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaceFormPreview() {
    KulinerNavTheme {
        PlaceFormScreen(initialPlace = null, isEditMode = false, onSave = { _, _, _, _, _ -> }, onCancel = {})
    }
}
