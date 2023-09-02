package com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CounterViewModel : ViewModel() {

    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> get() = _counter
    var isCounterEnabled = false

    init {
        _counter.value = 0
    }

    fun counterState(enabled: Boolean) {

        if (enabled) {
            isCounterEnabled = true
            while (true) {
                viewModelScope.launch {
                    delay(1000)
                    count()
                }
            }
        } else {
            isCounterEnabled = false
            return
        }
    }


    private fun count() {
        _counter.value = _counter.value?.plus(1)
    }
}
