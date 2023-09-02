package com.thatta.counterclassicxmlviewsmvvm.domain.usesCases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterUseCase(private val coroutineScope: CoroutineScope) {

    private var counterJob: Job? = null
    private var isCounterEnabled: Boolean = false
    private var counter: Int = 0

    //Method to start counter
    fun startCounter() {
        stopCounter()
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
        counter = 0
    }


    //Method to count
    private fun count() {
        if (isCounterEnabled) {
            // count 10 in 1 second
            counter ++
        }
    }

    // Method to get counter
    fun getCounter(): Int {
        return counter
    }

}