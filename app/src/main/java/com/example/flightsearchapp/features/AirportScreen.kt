@file:OptIn(ExperimentalMaterial3Api::class)    //TODO -> SearchBar

package com.example.flightsearchapp.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun FlightScreen(
    modifier: Modifier = Modifier,
    flightViewModel: FlightViewModel = hiltViewModel(),
) {

    val airportUiState = flightViewModel
        .airportUiState
        .collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            SearchBar(
                query = airportUiState.value.name,      //equivalente a value
                onQueryChange = { name ->                           //equivalente a onValueChange
                    flightViewModel.onQueryChange(name)
                },
                onSearch = {},                                      //cuando se apreta el icono de busqueda del teclado
                active = true,                                      //si la barra de busqueda esta activa o no -- equivalente a value
                onActiveChange = {},                                //cambio de estado en la barra de busqueda equivalente a onValueChange
                modifier = Modifier.wrapContentHeight(),
                placeholder = { Text(text = "Search") },            //muestra una sugerencia cuando no tiene el foco
                leadingIcon = {                                     //icono principal
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null
                        )
                    }
                }
            ) {
            }
        }
    }
}


/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FlightScreenPreview() {
    FlightSearchAppTheme {
        FlightScreen()
    }
}*/