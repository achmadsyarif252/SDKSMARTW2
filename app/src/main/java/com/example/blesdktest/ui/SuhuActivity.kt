package com.example.blesdktest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.blesdktest.viewModel.SmartWViewModel
import com.example.blesdktest.databinding.ActivitySuhuBinding
import com.example.blesdktest.smartwatch.WriteResponse

class SuhuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuhuBinding
    private lateinit var smartWViewModel: SmartWViewModel
    var writeResponse: WriteResponse = WriteResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuhuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

    }

    private fun setupViewModel() {
        smartWViewModel = ViewModelProvider(this)[SmartWViewModel::class.java]
        smartWViewModel.tmpData.observe(this) {
            showTemp(it)
        }
        smartWViewModel.cekTmp()
    }

    private fun showTemp(tmp: Float?) {
        binding.suhuValue.text = tmp.toString()
    }

}