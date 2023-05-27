package com.example.mobile_dev.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityOtpregisterBinding

class OTPRegister : AppCompatActivity() {

    private lateinit var binding: ActivityOtpregisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpregisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}