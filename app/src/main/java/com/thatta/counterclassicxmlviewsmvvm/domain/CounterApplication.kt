package com.thatta.counterclassicxmlviewsmvvm.domain

import android.app.Application
import com.thatta.counterclassicxmlviewsmvvm.data.local.AppDatabase
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// Application class to initialize the database, repository, use cases and be able to inject them
class CounterApplication: Application() {

    // by lazy to initialize the database only when it's needed
    private val appDatabase by lazy { AppDatabase.getDatabase(this) }

    val dataRepository by lazy {
        DataRepository(
            appDatabase.flagsDao()
        )
    }

    val counterUseCase by lazy {
        CounterUseCase(CoroutineScope(SupervisorJob()), dataRepository)
    }
    val insertFlagUseCase by lazy {
        InsertFlagUseCase()
    }
}