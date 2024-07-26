@file:OptIn(ExperimentalMaterial3Api::class)    //TODO -> SearchBar

package com.example.flightsearchapp.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearchapp.data.model.Airport

@Composable
fun AirportScreen(
    modifier: Modifier = Modifier,
    airportViewModel: AirportViewModel = hiltViewModel()
) {

    val airportUiState = airportViewModel
        .airportUiState
        .collectAsState()

    Box(
        modifier = Modifier
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {

        val onQuery = airportUiState.value.nameUi

        SearchBar(
            query = onQuery,                //equivalente a value
            onQueryChange = { name ->                                   //equivalente a onValueChange
                airportViewModel.onQueryChange(name)
            },
            onSearch = {},                                      //cuando se apreta el icono de busqueda del teclado
            active = true,                                      //si la barra de busqueda esta activa o no -- equivalente a value
            onActiveChange = {},                                //cambio de estado en la barra de busqueda equivalente a onValueChange
            modifier = Modifier.wrapContentHeight(),
            placeholder = { Text(text = "SearchPlaceHolder") },            //muestra una sugerencia cuando no tiene el foco
            leadingIcon = {                                                 //icono principal
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null) }
            },
            trailingIcon = { IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null )
                }
            }
        ) {

            if (onQuery.isNotEmpty() ) {
                LazyColumn(
                    modifier = modifier
                ) {
                    items(airportUiState.value.airportListUi) { airport ->
                        AirportItem(airportItem = airport)
                    }
                }
            }

        }
    }
}


@Composable
fun AirportItem(
    airportItem: Airport
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = airportItem.name,
            fontSize = 15.sp,
            fontStyle = FontStyle.Normal)
    }
}