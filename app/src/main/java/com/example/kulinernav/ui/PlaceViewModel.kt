package com.example.kulinernav.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.kulinernav.data.Place
import com.example.kulinernav.data.PlaceRepository

/** Menyimpan daftar tempat (in-memory) dan operasi tambah/edit/hapus. */
class PlaceViewModel : ViewModel() {

    private val _places = mutableStateListOf<Place>().apply {
        addAll(PlaceRepository.initialPlaces)
    }
    val places: List<Place> get() = _places

    /** Mengembalikan null bila id tidak ditemukan (kasus invalid ID). */
    fun getPlace(id: Int): Place? = _places.find { it.id == id }

    fun addPlace(
        name: String, category: String, location: String,
        rating: Double, description: String
    ): Int {
        val newId = (_places.maxOfOrNull { it.id } ?: 0) + 1
        _places.add(Place(newId, name, category, location, rating, description))
        return newId
    }

    fun updatePlace(updated: Place) {
        val index = _places.indexOfFirst { it.id == updated.id }
        if (index != -1) _places[index] = updated
    }

    fun deletePlace(id: Int) {
        _places.removeAll { it.id == id }
    }
}
