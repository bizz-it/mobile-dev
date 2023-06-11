package com.example.mobile_dev.ui.agreement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProgressFourBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.detail.DetailActivity
import com.example.mobile_dev.ui.theme.MobiledevTheme

class ProgressFour : AppCompatActivity() {

    private lateinit var binding: ActivityProgressFourBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressFourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true,
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@ProgressFour, ProgressThree::class.java)
                        startActivity(i)
                    }
                )
            }
        }

        binding.nextBtn.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.take),
                    onClick = {
                        val i = Intent(this@ProgressFour, DetailActivity::class.java)
                        startActivity(i)
                    }
                )
            }
        }
    }
}