package com.thatta.counterclassicxmlviewsmvvm.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.thatta.counterclassicxmlviewsmvvm.R
import com.thatta.counterclassicxmlviewsmvvm.databinding.ActivityMainBinding
import com.thatta.counterclassicxmlviewsmvvm.presentation.viewModels.CounterViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val counterViewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initListeners()
        initObservers()

    }

    private fun initViews() {
        if (counterViewModel.isCounterEnabled) {
            binding.btnMainActivityStartCounter.text = this.getString(R.string.stop_counter)
        } else {
            binding.btnMainActivityStartCounter.text = this.getString(R.string.start_counter)
        }
    }

    private fun initListeners() {
        binding.btnMainActivityStartCounter.setOnClickListener {
            if (!counterViewModel.isCounterEnabled) {
                counterViewModel.counterState(true)
                binding.btnMainActivityStartCounter.text = this.getString(R.string.stop_counter)
            } else {
                counterViewModel.counterState(false)
                binding.btnMainActivityStartCounter.text = this.getString(R.string.start_counter)

            }
        }
    }

    private fun initObservers() {
        counterViewModel.counter.observe(this) {
            binding.tvMainActivityCounter.text = it.toString()
        }
    }

}