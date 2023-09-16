package com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCaseInterface
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.GetAllFlagsUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val counterUseCase: CounterUseCaseInterface,
    private val insertFlagUseCase: InsertFlagUseCaseInterface,
    private val getFlagsUseCase: GetAllFlagsUseCase
) : ViewModel() {


    // UI vars
    // MutableLiveData for counter to use internally in this ViewModel and change it's value
    private val _counter = MutableLiveData<Int>()

    // LiveData for counter to be observe in other classes, get value from _counter
    val counter: LiveData<Int> get() = _counter

    // Same as above but for isCounterEnabled
    private val _isCounterEnabled = MutableLiveData<Boolean>()
    val isCounterEnabled: LiveData<Boolean> get() = _isCounterEnabled

    // LiveData for flags to be observe in other classes
    private val _allFlags = MutableLiveData<List<Int>>()
    val allFlags: LiveData<List<Int>> get() = _allFlags

    // Observers, late init to be initialized in init block
    private lateinit var counterObserver: Observer<Int>
    private lateinit var allFlagsObserver: Observer<List<FlagsModel>>

    init {
        initCounterObservers()
        initAllFlagsObserver()
    }

    // Remove observers when ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        // Remove observers
        counterUseCase.counter.removeObserver(counterObserver)
        getFlagsUseCase.allFlags.removeObserver(allFlagsObserver)
    }

    // Method to observe counter from counterUseCase
    private fun initCounterObservers() {
        // Create an Observer to observe counterUseCase.counter
        counterObserver = Observer<Int> { counterFromUseCase ->
            _counter.value = counterFromUseCase
        }
        // register counterObserver
        counterUseCase.counter.observeForever(counterObserver)
    }

    // Method to observe allFlags from getFlagsUseCase
    private fun initAllFlagsObserver() {

        // Create an Observer to observe getFlagsUseCase.allFlags
        allFlagsObserver = Observer { flagsFromUseCase ->
            _allFlags.value = flagsFromUseCase.map { it.flag }
        }
        // register allFlagsObserver
        getFlagsUseCase.allFlags.observeForever(allFlagsObserver)
    }


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
            insertFlagUseCase.insertFlag(_counter.value ?: 0)
        }
    }

}
