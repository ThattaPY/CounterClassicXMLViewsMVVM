package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

object InsertFlagUseCase {
    suspend fun insertFlag(repository: DataRepository, flag: String) {
        val flagsModel = FlagsModel(flag = flag)
        repository.insertFlag(flagsModel)
    }
}