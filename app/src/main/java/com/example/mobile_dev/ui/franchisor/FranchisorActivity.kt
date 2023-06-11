package com.example.mobile_dev.ui.franchisor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityFranchisorBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class FranchisorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFranchisorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFranchisorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true,
                    getString(R.string.franchisor),
                    onClick = { finish() }
                )
            }
        }

        binding.composeButton.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.start_registration),
                    onClick = { finish() }
                )
            }
        }
    }
}