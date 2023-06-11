package com.example.mobile_dev.ui.agreement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProgressTwoBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class ProgressTwo : AppCompatActivity() {

    private lateinit var binding: ActivityProgressTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true,
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@ProgressTwo, ProgressOne::class.java)
                        startActivity(i)
                    }
                )
            }
        }

        binding.nextBtn.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.next),
                    onClick = {
                        val i = Intent(this@ProgressTwo, ProgressThree::class.java)
                        startActivity(i)
                    }
                )
            }
        }
    }

}