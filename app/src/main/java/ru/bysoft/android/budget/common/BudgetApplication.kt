package ru.bysoft.android.budget.common

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import ru.bysoft.android.budget.features.currency_rates.data.storage.CurrentRaceDatabase

@HiltAndroidApp
class BudgetApplication : Application(){
    val database: CurrentRaceDatabase by lazy {
        Room.databaseBuilder(this, CurrentRaceDatabase::class.java, "current-race-database").build()
    }
}