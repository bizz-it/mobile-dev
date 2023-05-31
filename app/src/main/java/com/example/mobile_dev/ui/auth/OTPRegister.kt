package com.example.mobile_dev.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.compose.theme.MobiledevTheme
import com.example.mobile_dev.databinding.ActivityOtpregisterBinding
import com.example.mobile_dev.ui.component.ButtonApp

class OTPRegister : AppCompatActivity() {

    private lateinit var binding: ActivityOtpregisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpregisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVerify.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.login),
                    onClick = {}
                )
            }
        }
    }
}