package com.thatta.counterclassicxmlviewsmvvm.presentation.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatta.counterclassicxmlviewsmvvm.R
import com.thatta.counterclassicxmlviewsmvvm.databinding.ActivityMainBinding
import com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels.CounterViewModel
import com.thatta.counterclassicxmlviewsmvvm.presentation.views.adapters.FlagsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val counterViewModel by viewModels<CounterViewModel>()

    private lateinit var flagsAdapter: FlagsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFlagsRecyclerView()

        initListeners()

        initObservers()

    }

    private fun initListeners() {
        binding.btnMainActivityStartCounter.setOnClickListener {
            counterViewModel.toggleCounter()
        }

        binding.ibMainActivityFlag.setOnClickListener {
            counterViewModel.insertFlag()
        }
    }

    private fun initObservers() {
        counterViewModel.counter.observe(this) {
            binding.tvMainActivityCounter.text = getString(R.string.value, it.toString())
        }

        // The observer has logic because it's UI-only logic
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

    private fun initFlagsRecyclerView() {
        val flagsList = mutableListOf<Int>()
        flagsAdapter = FlagsAdapter(this, flagsList)
        val flagsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.rvMainActivityFlagsList.layoutManager = flagsLayoutManager
        binding.rvMainActivityFlagsList.adapter = flagsAdapter
    }

}