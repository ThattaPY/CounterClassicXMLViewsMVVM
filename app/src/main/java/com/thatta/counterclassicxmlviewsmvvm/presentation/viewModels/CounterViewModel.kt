package com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCaseInterface
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.GetAllFlagsUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.GetAllFlagsUseCaseInterface
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private var counterUseCase: CounterUseCaseInterface,
    private val insertFlagUseCase: InsertFlagUseCaseInterface,
    private val getFlagsUseCase: GetAllFlagsUseCaseInterface
) : ViewModel() {

    // LiveData for counter to be observe in other classes, get value from counterUseCase
    val counter: LiveData<Int> get() = counterUseCase.counter


    // LiveData for isCounterEnabled to be observe in other classes,
    // used private MutableLiveData to change value in this class and LiveData to be observed in other classes
    private val _isCounterEnabled = MutableLiveData<Boolean>()
    val isCounterEnabled: LiveData<Boolean> get() = _isCounterEnabled

    // LiveData for allFlags to be observe in other classes, get value from getFlagsUseCase
    // getFlagsUseCase returns a Flow, so we need to convert it to LiveData to be used in the UI
    val allFlags: LiveData<List<Int>> get() = getFlagsUseCase.allFlags.asLiveData()


    //Method to start counter
    private fun startCounter() {
        counterUseCase.startCounter()
        _isCounterEnabled.value = true
    }

    //Method to stop counter
    private fun stopCounter() {
        counterUseCase.stopCounter()
        _isCounterEnabled.value = false
    }

    // Method so start or stop counter depending on isCounterEnabled
    fun toggleCounter() {
        isCounterEnabled.value.let {
            if (it == true) {
                stopCounter()
            } else if (it == false || it == null) {
                startCounter()
            }
        }
    }

    //Method to insert flag
    fun insertFlag() {
        viewModelScope.launch(Dispatchers.IO) {
            insertFlagUseCase.insertFlag(counter.value ?: 0)
        }
    }

}
