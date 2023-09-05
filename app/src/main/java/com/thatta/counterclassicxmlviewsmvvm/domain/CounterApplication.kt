package com.thatta.counterclassicxmlviewsmvvm.domain

import android.app.Application
import com.thatta.counterclassicxmlviewsmvvm.data.local.AppDatabase
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CounterApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

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