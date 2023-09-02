package com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterViewModel(private val repository: DataRepository) : ViewModel() {

    // UI vars
    // LiveData for counter to use internally in this ViewModel
    private val _counter = MutableLiveData<Int>()

    // LiveData for counter to be observe in other classes
    val counter: LiveData<Int> get() = _counter

    // Same as above but for isCounterEnabled
    private val _isCounterEnabled = MutableLiveData<Boolean>()
    val isCounterEnabled: LiveData<Boolean> get() = _isCounterEnabled

    private val counterUseCase = CounterUseCase(viewModelScope)


    init {
        counterUseCase.getCounter().let {
            _counter.value = it
        }
    }

    //Method to start counter
    fun startCounter() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFlags()
        }
        counterUseCase.startCounter()
        _isCounterEnabled.value = true
        viewModelScope.launch(Dispatchers.Main) {
            while (_isCounterEnabled.value!!) {
                delay(70)
                _counter.postValue(counterUseCase.getCounter())
            }
        }
    }

    //Method to stop counter
    fun stopCounter() {
        counterUseCase.stopCounter()
        _isCounterEnabled.value = false
        _counter.postValue(counterUseCase.getCounter())
    }

    fun insertFlag() {
        viewModelScope.launch(Dispatchers.IO) {
            InsertFlagUseCase.insertFlag(counterUseCase.getCounter().toString())
        }
    }


    // Factory for constructing CounterViewModel with parameter
    class BrowserViewModelFactory(private val repository: DataRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CounterViewModel::class.java)) {
                return CounterViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
