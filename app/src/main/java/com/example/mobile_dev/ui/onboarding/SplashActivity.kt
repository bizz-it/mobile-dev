package com.example.mobile_dev.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.databinding.ActivitySplashBinding
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
            if (!isLogin) {
                val intent = Intent(this@SplashActivity, DetailActivity::class.java)
                startActivity(intent)
            } else {
                val bundle = ActivityOptionsCompat.makeCustomAnimation(
                    this@SplashActivity,
                    R.anim.fade_in, R.anim.fade_out
                ).toBundle()
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent, bundle)
            }
            finish()
        }, DURATION)
    }

    companion object {
        const val DURATION: Long = 1500
    }
}