package com.example.flightsearchapp.features

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.entities.FavoritesEntity
import com.example.flightsearchapp.data.repository.FavoriteRepository
import com.example.flightsearchapp.data.repository.RepositoryApp
import com.example.flightsearchapp.model.FlightsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val repositoryApp: RepositoryApp,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {


    var airportUiState by mutableStateOf(AirportUiState())

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")

    fun onQueryChange(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listSuggestions = repositoryApp.getSuggestionsAirports(value)
            airportUiState = airportUiState.copy(nameUi = value, listSuggestionsAirport = listSuggestions)
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun getFlights(name: String, iataCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val flightsList =  repositoryApp.getAllAirportsDB().map {
                FlightsModel(
                    flightsDepartCode = iataCode,
                    flightsDepartName = name,
                    flightsArriveName = it.name,
                    flightsArriveCode = it.iataCode
                )
            }.filter {
                name != it.flightsArriveName
            }
            airportUiState = airportUiState.copy(flightsList = flightsList as ArrayList<FlightsModel>)
        }
    }

    fun saveFavoriteFlights(item: FlightsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.insertFavorite(
                FavoritesEntity(
                    departureCode = item.flightsDepartCode,
                    destinationCode = item.flightsArriveCode
                )
            )
        }
    }

}