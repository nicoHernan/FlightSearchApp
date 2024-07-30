package com.example.flightsearchapp.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.RepositoryApp
import com.example.flightsearchapp.data.model.Airport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(private val repositoryApp: RepositoryApp): ViewModel() {

    private val _airportUiState = MutableStateFlow(AirportUiState() )
    val airportUiState: StateFlow<AirportUiState> = _airportUiState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val airport = repositoryApp.getAllAirportsDB().map { airportEntity ->
                    Airport(
                        id = airportEntity.id,
                        iataCode = airportEntity.iataCode,
                        name = airportEntity.name,
                        passengers = airportEntity.passengers
                    )
                } as ArrayList<Airport>

                _airportUiState.update {
                    _airportUiState.value.copy(airportListUi = airport)
                }
            }
        }
    }


    fun onQueryChange(value: String) {
        _airportUiState.update {
            _airportUiState.value.copy(nameUi = value)
        }
    }
}