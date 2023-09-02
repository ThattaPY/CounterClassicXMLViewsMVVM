package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterUseCase(
    private val coroutineScope: CoroutineScope,
    private val repository: DataRepository
) {

    private var counterJob: Job? = null
    private var isCounterEnabled: Boolean = false

    // LiveData counter to be observe in other classes
    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> get() = _counter

    //Method to start counter
    fun startCounter() {
        stopCounter()
        deleteAllFlags()
        _counter.value = 0
        isCounterEnabled = true
        counterJob = coroutineScope.launch(Dispatchers.IO) {
            while (isCounterEnabled) {
                delay(100)
                count()
            }
        }
    }

    //Method to stop counter
    fun stopCounter() {
        isCounterEnabled = false
        counterJob?.cancel()
        _counter.value = 0
    }


    //Method to count
    private fun count() {
        if (isCounterEnabled) {
            // count 10 in 1 second
            _counter.postValue(_counter.value?.plus(1))
        }
    }


    private fun deleteAllFlags() {
        coroutineScope.launch(Dispatchers.IO) {
            repository.deleteAllFlags()
        }
    }

}