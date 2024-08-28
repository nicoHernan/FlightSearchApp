package com.example.flightsearchapp.features

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.data.entities.AirportEntity
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

    private val _isSearchingUiState = MutableStateFlow(false)
    val isSearchingUiState = _isSearchingUiState.asStateFlow()

    private val _searchText = MutableStateFlow("")

    private fun validateFavoriteFlights() {
        viewModelScope.launch(Dispatchers.IO) {
            val flightsList = favoriteRepository.getFavorites().map {
                FlightsModel(
                    flightsDepartCode = it.departureCode,
                    flightsDepartName = repositoryApp.getNameAirportByIataCode(it.departureCode),
                    flightsArriveName = repositoryApp.getNameAirportByIataCode(it.destinationCode),
                    flightsArriveCode = it.destinationCode
                )
            }
            setSearchingState(false)
            airportUiState = airportUiState.copy(flightsList = flightsList as ArrayList<FlightsModel>)
        }
    }
    fun onQueryChange(value: String) {
        val listSuggestions = emptyList<AirportEntity>()
        if(value.isEmpty()) {
            validateFavoriteFlights()
        }else {
            viewModelScope.launch(Dispatchers.IO) {
                 repositoryApp.getSuggestionsAirports(value)
            }
        }
        airportUiState = airportUiState.copy(nameUi = value, listSuggestionsAirport = listSuggestions)
    }

    fun getFlights(name: String, iataCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setSearchingState(false)
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

    fun setSearchingState(isSearching: Boolean){
        _isSearchingUiState.value = isSearching
    }

}