package com.example.algeriantraditionsculture.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.algeriantraditionsculture.data.model.Location
import com.example.algeriantraditionsculture.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    private val _filteredLocations = MutableLiveData<List<Location>>()
    val filteredLocations: LiveData<List<Location>> = _filteredLocations

    private var currentFilter: FilterType = FilterType.NONE
    private var currentQuery: String = ""

    init {
        loadAllLocations()
    }

    private fun loadAllLocations() {
        viewModelScope.launch {
            try {
                val allLocations = locationRepository.getAllLocations()
                _locations.value = allLocations
                applyFilters()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun searchLocations(query: String) {
        currentQuery = query
        applyFilters()
    }

    fun filterByRegion() {
        currentFilter = if (currentFilter == FilterType.REGION) {
            FilterType.NONE
        } else {
            FilterType.REGION
        }
        applyFilters()
    }

    fun filterBySeason() {
        currentFilter = if (currentFilter == FilterType.SEASON) {
            FilterType.NONE
        } else {
            FilterType.SEASON
        }
        applyFilters()
    }

    private fun applyFilters() {
        val locations = _locations.value ?: return
        var filtered = locations

        // Apply search query filter
        if (currentQuery.isNotEmpty()) {
            filtered = filtered.filter { location ->
                location.name.contains(currentQuery, ignoreCase = true) ||
                location.description.contains(currentQuery, ignoreCase = true)
            }
        }

        // Apply type filter
        filtered = when (currentFilter) {
            FilterType.REGION -> filtered.filter { it.type == "region" }
            FilterType.SEASON -> filtered.filter { it.type == "season" }
            FilterType.NONE -> filtered
        }

        _filteredLocations.value = filtered
    }

    private enum class FilterType {
        NONE, REGION, SEASON
    }
} 