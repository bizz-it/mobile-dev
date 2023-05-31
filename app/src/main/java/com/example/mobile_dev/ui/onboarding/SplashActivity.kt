package com.example.mobile_dev.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.MainActivity
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.databinding.ActivitySplashBinding
import com.example.mobile_dev.ui.auth.LoginActivity
import com.example.mobile_dev.ui.auth.dataStore
import com.example.mobile_dev.ui.detail.DetailActivity

class SplashActivity :  AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var isLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = UserPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]
        mainViewModel.getUserData().observe(this) {
            isLogin = it.token.isEmpty()
        }

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) {
                val intent = Intent(this@SplashActivity, DetailActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, DURATION)
    }

    companion object {
        const val DURATION: Long = 3500
    }
}