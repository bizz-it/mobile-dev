package com.example.mobile_dev.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityDetailBinding
import com.example.mobile_dev.ui.agreement.ProgressTwo
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    false,
                    getString(R.string.detail),
                    onClick = { finish() }
                )
            }
        }

        binding.composeButton.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@DetailActivity, ProgressTwo::class.java)
                        startActivity(i)
                    }
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}