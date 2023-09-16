package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import javax.inject.Inject


interface InsertFlagUseCaseInterface {
    suspend fun insertFlag(flag: Int)
}

class InsertFlagUseCase @Inject constructor(private val repository: DataRepository) :
    InsertFlagUseCaseInterface {

    // Method to insert flag, the repository is injected from the view model
    override suspend fun insertFlag(flag: Int) {
        if (flag != 0) {
            val flagsModel = FlagsModel(flag = flag)
            repository.insertFlag(flagsModel)
        }
    }
}