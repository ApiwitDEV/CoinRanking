package com.example.ai.uilayer.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ai.databinding.ActivityCoinDetailBinding
import com.example.ai.uilayer.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding
    private val viewModel by viewModel<DetailViewModel>()

    var uuid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uuid = intent.getStringExtra("uuid")?:""
        observeViewModel()
        onViewModelObserved()
        initView()
    }

    private fun observeViewModel() {
        viewModel.apply {
            coinDetail.observe(this@CoinDetailActivity) {
                binding.detail.text = it?.toString()
            }
        }
    }

    private fun onViewModelObserved() {
        viewModel.getIndividualCoinDetail(uuid)
    }

    private fun initView() {

    }
}