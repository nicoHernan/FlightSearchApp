@file:OptIn(ExperimentalMaterial3Api::class)    //TODO -> SearchBar

package com.example.flightsearchapp.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearchapp.data.entities.AirportEntity
import com.example.flightsearchapp.model.FlightsModel

@Composable
fun AirportScreen(
    modifier: Modifier = Modifier,
    airportViewModel: AirportViewModel = hiltViewModel()
) {

    val airportUiState = airportViewModel
        .airportUiState

    val isSearching by airportViewModel.isSearching.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                query = airportUiState.nameUi,
                onQueryChange = airportViewModel::onQueryChange,
                onSearch = airportViewModel::onQueryChange,
                active = isSearching,
                onActiveChange = { airportViewModel.onToogleSearch() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(airportUiState.listSuggestionsAirport) { airport ->
                        AirportItem(airportItem = airport) {
                            airportViewModel.getFlights(airport.name,airport.iataCode)
                        }
                    }
                }
            }
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(airportUiState.flightsList) {
                    FlightsFromItem(flightsFromItem = it)
                }
            }
        }
    }
}


@Composable
fun AirportItem(
    airportItem: AirportEntity,
    onClickItem:() -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                onClickItem()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = airportItem.iataCode ?: "",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.width(50.dp) )
        
        Text(
            text = airportItem.name ?: "",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic)
    }
}

@Composable
fun FlightsFromItem(
    flightsFromItem: FlightsModel,
    airportViewModel: AirportViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp)
        ) {
            val (textDepart, layoutIataCode,layoutArrive, textArrive, iconStart) = createRefs()
            Text(                           //DEPART IATACODE
                text = "Depart",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.constrainAs(textDepart) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .constrainAs(layoutIataCode) {
                        top.linkTo(textDepart.bottom, 5.dp)
                        start.linkTo(parent.start)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(                           //DEPART IATACODE
                    text = flightsFromItem.flightsDepartCode,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(                           //DEPART NAME AIRPORT
                    text = flightsFromItem.flightsDepartName,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Text(                           //DEPART IATACODE
                text = "Arrive",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.constrainAs(textArrive) {
                    top.linkTo(layoutIataCode.bottom)
                    start.linkTo(parent.start)
                }
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .constrainAs(layoutArrive) {
                        top.linkTo(textArrive.bottom, 5.dp)
                        start.linkTo(parent.start)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(                             //ARRIVE IATACODE
                    text = flightsFromItem.flightsArriveCode,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(                             //ARRIVE NAME AIRPORT
                    text = flightsFromItem.flightsArriveName,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            
            Icon(
                Icons.Filled.Star,
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(iconStart) {
                        end.linkTo(parent.end, 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(30.dp)
                    .clickable {
                        airportViewModel.saveFavoriteFlights(flightsFromItem)
                    }
            )
        }

    }
}