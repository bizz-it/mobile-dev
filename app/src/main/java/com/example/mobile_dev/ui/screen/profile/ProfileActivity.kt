package com.example.mobile_dev.ui.screen.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProfileBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    false,
                    getString(R.string.menu_profile),
                    onClick = { finish() }
                )
            }
        }
    }
}