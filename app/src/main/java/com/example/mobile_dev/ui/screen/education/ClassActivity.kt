package com.example.mobile_dev.ui.screen.education

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityClassBinding
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

class ClassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    false,
                    getString(R.string.menu_class),
                    onClick = { finish() }
                )
            }
        }
    }
}