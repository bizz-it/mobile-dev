package com.example.mobile_dev.ui.agreement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProgressThreeBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class ProgressThree : AppCompatActivity() {

    private lateinit var binding: ActivityProgressThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProgressThreeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@ProgressThree, ProgressTwo::class.java)
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
                        val i = Intent(this@ProgressThree, ProgressFour::class.java)
                        startActivity(i)
                    }
                )
            }
        }
    }
}