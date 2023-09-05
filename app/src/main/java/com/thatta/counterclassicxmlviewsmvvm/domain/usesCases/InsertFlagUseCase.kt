package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

class InsertFlagUseCase {

    // Method to insert flag, the repository is injected from the view model
    suspend fun insertFlag(repository: DataRepository, flag: Int) {
        val flagsModel = FlagsModel(flag = flag)
        repository.insertFlag(flagsModel)
    }
}