package com.example.flightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")      //además está la tabla favorite para insertar con dataStore
data class AirportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "IATA_CODE")                 //Código IATA de 3 letras
    val iata_code: String,
    @ColumnInfo(name = "FULLNAME_AIRPORT")          //Nombre completo del aeropuerto
    val name: String,
    @ColumnInfo(name = "NUMBER_OF_PASSENGERS")     //Cantidad de pasajeros por año
    val passengers: Int
)
