package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetAllFlagsUseCaseInterface {
    val allFlags: Flow<List<Int>>
}

class GetAllFlagsUseCase @Inject constructor(private val repository: DataRepository) :
    GetAllFlagsUseCaseInterface {

        // Get all flags from repository and map them to a list of flags
        // sorted by flag value. Exposure of the flags as a Flow
    override val allFlags: Flow<List<Int>>
        get() = repository.getAllFlags().map { flags ->
            flags.sortedBy { it.flag }
                .map { it.flag }
        }

}