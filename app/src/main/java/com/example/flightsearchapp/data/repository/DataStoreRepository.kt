package com.example.flightsearchapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearchapp.data.entities.AirportEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "userStore")
class DataStoreRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    companion object {
        private const val AIRPORT_NAME = "NANE"
        private const val IATACODE = "CODE"
    }
    suspend fun setValue(value: AirportEntity) {
        context.dataStore.edit { preference ->
            preference[stringPreferencesKey(AIRPORT_NAME)] = value.name
            preference[stringPreferencesKey(IATACODE)] = value.iataCode
        }
    }

    suspend fun getValue(): Flow<AirportEntity> {
        return  context.dataStore.data.map { preference ->
            AirportEntity(
                id = 0,
                iataCode = preference[stringPreferencesKey(IATACODE)] ?: "" ,
                name = preference[stringPreferencesKey(AIRPORT_NAME)] ?: "" ,
                passengers = 0
            )

        }
    }

}