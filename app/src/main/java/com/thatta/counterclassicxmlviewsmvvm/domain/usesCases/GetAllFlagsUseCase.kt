package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import javax.inject.Inject

interface GetAllFlagsUseCaseInterface {
    val allFlags: LiveData<List<FlagsModel>>
}

class GetAllFlagsUseCase @Inject constructor(private val repository: DataRepository) : GetAllFlagsUseCaseInterface {

    // LiveData for flags to be observe in other classes,
    // mutable to be able to change it's value internally
    private val _allFlags = MutableLiveData<List<FlagsModel>>()
    override val allFlags: LiveData<List<FlagsModel>> get() = _allFlags

    // init block to get all flags from repository
    init {
        getAllFlags()
    }

    // Method to get all flags from repository
    private fun getAllFlags() {
        repository.getAllFlags().observeForever { flagsFromRepository ->
            _allFlags.value = flagsFromRepository.sortedBy { it.flag }
        }
    }

}