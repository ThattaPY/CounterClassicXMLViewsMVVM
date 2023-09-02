package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import com.thatta.counterclassicxmlviewsmvvm.domain.CounterApplication
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

object InsertFlagUseCase {
    private val application = CounterApplication()
    private val repository = application.dataRepository
    suspend fun insertFlag(flag: String) {
        val flagsModel = FlagsModel(flag = flag)
        repository.insertFlag(flagsModel)
    }
}