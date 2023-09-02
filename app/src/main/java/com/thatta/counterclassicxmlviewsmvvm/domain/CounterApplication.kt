package com.thatta.counterclassicxmlviewsmvvm.domain

import android.app.Application
import com.thatta.counterclassicxmlviewsmvvm.data.local.AppDatabase
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CounterApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val appDatabase by lazy { AppDatabase.getDatabase(this, applicationScope) }

    val dataRepository by lazy {
        DataRepository(
            appDatabase.flagsDao()
        )
    }
}