package com.demo.clientapplication.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.demo.clientapplication.R
import com.demo.clientapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bindViewModel()
        setListeners()
    }

    private fun bindViewModel() {
        viewModel.response.observe(this) {
            binding.textView.text = it
        }
    }

    private fun setListeners() {
        binding.httpRequestButton.setOnClickListener {
            viewModel.onHttpRequestButtonPressed()
        }
        binding.tcpRequestButton.setOnClickListener {
            viewModel.onTcpRequestButtonPressed()
        }
    }
}