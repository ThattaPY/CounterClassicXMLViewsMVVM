package com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
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

    // LiveData for flags to be observe in other classes
    private val _allFlags = MutableLiveData<List<String>>()
    val allFlags: LiveData<List<String>> get() = _allFlags

    // UseCase
    private val counterUseCase = CounterUseCase(viewModelScope, repository)


    init {
        initObservers()
    }

    private fun initObservers() {
        counterUseCase.counter.observeForever { counterFromUseCase ->
            _counter.value = counterFromUseCase
        }

        repository.getAllFlags().observeForever { flagsFromRepo ->
            _allFlags.value = flagsFromRepo.map { it.flag }
        }
    }

    //Method to start counter
    fun startCounter() {
        counterUseCase.startCounter()
        _isCounterEnabled.value = true
    }

    //Method to stop counter
    fun stopCounter() {
        counterUseCase.stopCounter()
        _isCounterEnabled.value = false
    }

    fun insertFlag() {
        viewModelScope.launch(Dispatchers.IO) {
            InsertFlagUseCase.insertFlag(repository, _counter.value?.toString() ?: "0")
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
