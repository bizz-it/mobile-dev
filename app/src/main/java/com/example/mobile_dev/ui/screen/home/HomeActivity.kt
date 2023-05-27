package com.example.mobile_dev.ui.screen.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityHomeBinding
import com.example.mobile_dev.databinding.ActivityRegisterBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}