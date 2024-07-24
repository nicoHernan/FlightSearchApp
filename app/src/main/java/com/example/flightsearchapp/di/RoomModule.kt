package com.example.flightsearchapp.di

import android.content.Context
import androidx.room.Room
import com.example.flightsearchapp.data.DataBaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Singleton
    @Provides
    fun providesRoomDB(
        @ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            DataBaseApp::class.java,
            "flight_search.db"
        )
        .createFromAsset("flight_search.db")
        .build()

@Singleton
@Provides
fun providesFlightDao(dataBaseApp: DataBaseApp) = dataBaseApp
    .flightDAO()
}
