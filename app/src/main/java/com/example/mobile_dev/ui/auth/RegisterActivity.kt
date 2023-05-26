package com.example.mobile_dev.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_dev.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< Updated upstream
        setContentView(R.layout.activity_register)
=======
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Okeee
        binding.login.setOnClickListener{
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(i)
        }
>>>>>>> Stashed changes
    }
}