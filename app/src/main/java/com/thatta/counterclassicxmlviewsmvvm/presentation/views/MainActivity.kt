package com.thatta.counterclassicxmlviewsmvvm.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatta.counterclassicxmlviewsmvvm.R
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import com.thatta.counterclassicxmlviewsmvvm.databinding.ActivityMainBinding
import com.thatta.counterclassicxmlviewsmvvm.domain.CounterApplication
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCase
import com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels.CounterViewModel
import com.thatta.counterclassicxmlviewsmvvm.presentation.views.adapters.FlagsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var counterViewModelFactory: CounterViewModel.CounterViewModelFactory
    private lateinit var counterViewModel: CounterViewModel
    private lateinit var application: CounterApplication
    private lateinit var repository: DataRepository
    private lateinit var counterUseCase: CounterUseCase
    private lateinit var insertFlagUseCase: InsertFlagUseCase
    private lateinit var flagsAdapter: FlagsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initApplicationClasses()

        initViewModels()

        initFlagsRecyclerView()

        initListeners()
        initObservers()

    }

    private fun initApplicationClasses() {
        application = getApplication() as CounterApplication
        repository = application.dataRepository
        counterUseCase = application.counterUseCase
        insertFlagUseCase = application.insertFlagUseCase
    }

    private fun initFlagsRecyclerView() {
        val flagsList = mutableListOf<String>()
        flagsAdapter = FlagsAdapter(flagsList)
        val flagsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.rvMainActivityFlagsList.layoutManager = flagsLayoutManager
        binding.rvMainActivityFlagsList.adapter = flagsAdapter
    }

    private fun initViewModels() {

        counterViewModelFactory =
            CounterViewModel.CounterViewModelFactory(repository, counterUseCase, insertFlagUseCase)
        counterViewModel =
            ViewModelProvider(this, counterViewModelFactory)[CounterViewModel::class.java]
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

        counterViewModel.allFlags.observe(this) {
            flagsAdapter.updateFlags(it, binding.rvMainActivityFlagsList)
        }
    }

}