package com.example.blesdktest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blesdktest.R
import com.example.blesdktest.databinding.ActivityBloodPressureBinding
import com.example.blesdktest.model.BPData

class BloodPressureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloodPressureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodPressureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bpData = intent.getParcelableExtra<BPData>(EXTRA_BP)
        populateView(bpData)
    }

    private fun populateView(data: BPData?) {
        binding.tvBpStatus.text = getString(R.string.bpStatus, data?.status)
        binding.tvBpProgress.text = getString(R.string.bpProgress, data?.progress)
        binding.tvHP.text = getString(R.string.hp, data?.highPressure)
        binding.tvLp.text = getString(R.string.lp, data?.lowPressure)
        binding.isHavePorgress.text =
            getString(R.string.isProgress, if (data?.isHaveProgress == true) "Ya" else "Tidak")
    }

    companion object {
        val EXTRA_BP = "EXTRA_BP"
    }
}