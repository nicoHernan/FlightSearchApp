package com.example.flightsearchapp.features

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.entities.AirportEntity
import com.example.flightsearchapp.data.entities.FavoritesEntity
import com.example.flightsearchapp.data.repository.DataStoreRepository
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
    private val favoriteRepository: FavoriteRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {


    var airportUiState by mutableStateOf(AirportUiState())

    private val _isSearchingUiState = MutableStateFlow(false)
    val isSearchingUiState = _isSearchingUiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
     init {
         validateFavoriteFlights()
     }
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
        var listSuggestions = emptyList<AirportEntity>()
        if(value.isEmpty()) {
            validateFavoriteFlights()
        }else {
            viewModelScope.launch(Dispatchers.IO) {
               listSuggestions = repositoryApp.getSuggestionsAirports(value)
                if(listSuggestions.isEmpty()) {
                    getLastSearch()
                }
            }
        }
        airportUiState = airportUiState.copy(nameUi = value, listSuggestionsAirport = listSuggestions)
    }

    fun getFlights(value: AirportEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            setLastSearch(value = value)
            setSearchingState(false)
            val flightsList =  repositoryApp.getAllAirportsDB().map {
                FlightsModel(
                    flightsDepartCode = value.iataCode,
                    flightsDepartName = value.name,
                    flightsArriveName = it.name,
                    flightsArriveCode = it.iataCode
                )
            }.filter {
                value.name != it.flightsArriveName
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

    private fun getLastSearch() {
        viewModelScope.launch {
            dataStoreRepository.getValue().collect {
                    val list = listOf(
                        AirportEntity(
                            id = it.id,
                            iataCode = it.iataCode,
                            name = it.name,
                            passengers = it.passengers
                        )
                    )
                    setSearchingState(true)
                    airportUiState = airportUiState.copy(nameUi = it.name, listSuggestionsAirport = list)
            }
        }
    }

    private suspend fun setLastSearch(value: AirportEntity) {
        dataStoreRepository.setValue(value)
    }

}