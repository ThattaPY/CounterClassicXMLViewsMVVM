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

interface CounterUseCaseInterface {
    fun startCounter()
    fun stopCounter()
    val counter: LiveData<Int>
}

class CounterUseCase(
    private val coroutineScope: CoroutineScope,
    private val repository: DataRepository
): CounterUseCaseInterface {

    private var counterJob: Job? = null
    private var isCounterEnabled: Boolean = false

    // LiveData counter to be observe in other classes
    private val _counter = MutableLiveData<Int>()
    override val counter: LiveData<Int> get() = _counter

    //Method to start counter
    override fun startCounter() {
        stopCounter()
        deleteAllFlags()
        _counter.value = 0
        isCounterEnabled = true
        // launch a coroutine to count with Job counterJob to be able to cancel it
        counterJob = coroutineScope.launch(Dispatchers.IO) {
            while (isCounterEnabled) {
                // count() get call every 0.1 second
                delay(100)
                count()
            }
        }
    }

    //Method to stop counter
    override fun stopCounter() {
        isCounterEnabled = false
        // cancel counterJob to prevent the acceleration of the counter
        counterJob?.cancel()
        _counter.value = 0
    }


    //Method to count
    private fun count() {
        if (isCounterEnabled) {
            // count 10 times every 1 second
            _counter.postValue(_counter.value?.plus(1))
        }
    }

    // Method to delete all flags
    private fun deleteAllFlags() {
        coroutineScope.launch(Dispatchers.IO) {
            repository.deleteAllFlags()
        }
    }

}