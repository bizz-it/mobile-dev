package com.example.mobile_dev.ui.franchisor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityFranchisorThreeBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class FranchisorThree : AppCompatActivity() {

    private lateinit var binding: ActivityFranchisorThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFranchisorThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true,
                    getString(R.string.franchise_information),
                    onClick = { finish() }
                )
            }
        }

        binding.nextBtn.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.next),
                    onClick = { finish() }
                )
            }
        }
    }
}