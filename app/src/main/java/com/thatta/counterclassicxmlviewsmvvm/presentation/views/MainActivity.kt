package com.thatta.counterclassicxmlviewsmvvm.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thatta.counterclassicxmlviewsmvvm.domain.CounterApplication
import com.thatta.counterclassicxmlviewsmvvm.R
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.databinding.ActivityMainBinding
import com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels.CounterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var counterViewModelFactory: CounterViewModel.BrowserViewModelFactory
    private lateinit var counterViewModel: CounterViewModel
    private lateinit var application: CounterApplication
    private lateinit var repository: DataRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        application = getApplication() as CounterApplication
        initViewModels()

        initListeners()
        initObservers()

    }

    private fun initViewModels() {
        repository = application.dataRepository
        counterViewModelFactory = CounterViewModel.BrowserViewModelFactory(repository)
        counterViewModel = ViewModelProvider(this, counterViewModelFactory)[CounterViewModel::class.java]
    }

    private fun initListeners() {
        binding.btnMainActivityStartCounter.setOnClickListener {
            counterViewModel.isCounterEnabled.value.let {
                if (it == true) {
                    counterViewModel.stopCounter()
                } else if (it == false || it == null) {
                    counterViewModel.startCounter()
                }
            }
        }

        binding.ibMainActivityFlag.setOnClickListener {
            counterViewModel.insertFlag()
        }
    }

    private fun initObservers() {
        counterViewModel.counter.observe(this) {
            binding.tvMainActivityCounter.text = it.toString()
        }

        counterViewModel.isCounterEnabled.observe(this) {
            if (it) {
                binding.btnMainActivityStartCounter.text = this.getString(R.string.stop_counter)
            } else {
                binding.btnMainActivityStartCounter.text = this.getString(R.string.start_counter)
            }
        }
    }

}