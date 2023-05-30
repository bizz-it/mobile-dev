package com.example.mobile_dev.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mobile_dev.data.Result
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.MainActivity
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.databinding.ActivityLoginBinding
import kotlin.system.exitProcess

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        playAnimation()

        binding.regist.setOnClickListener{
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
        binding.apply {
            btnLogin.setOnClickListener{
                val email = emailInput.text.toString()
                val pass = passInput.text.toString()
                when {
                    email.isEmpty() -> {
                        emailInput.error = resources.getString(R.string.emptymail)
                    }
                    !email.contains('@') -> {
                        emailInput.error = resources.getString(R.string.errormail)
                    }
                    pass.isEmpty() -> {
                        passInput.error = resources.getString(R.string.emptypass)
                    }
                    pass.length < 6 -> {
                        passInput.error = resources.getString(R.string.errorpass)
                    }
                    else -> { login(email, pass)}
                }
            }
        }
    }

    private fun login(email: String, pass: String) {
        val pref = this.dataStore
        val data = UserPreferences.getInstance(pref)
        val viewModel = ViewModelProvider(this, SettingFactory(data))[SettingViewModel::class.java]

        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        authViewModel.login(email, pass).observe(this) { result ->
            if (result != null) {
                dialog.apply {
                    when (result) {
                        is Result.Loading -> {
                            show()
                        }
                        is Result.Success -> {
                            cancel()
                                val i = Intent(this@LoginActivity, MainActivity::class.java)
                                viewModel.saveUserData(result.data?.value)
                                Toast.makeText(this@LoginActivity, resources.getString(R.string.successlog), Toast.LENGTH_SHORT).show()
                                startActivity(i)
                        }
                        is Result.Error -> {
                            cancel()
                            Toast.makeText(this@LoginActivity, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

//    private fun playAnimation() {
//        binding.apply {
//            val email = ObjectAnimator.ofFloat(email, View.ALPHA, 1f).setDuration(DURATION)
//            val pass = ObjectAnimator.ofFloat(pass, View.ALPHA, 1f).setDuration(DURATION)
//
//            AnimatorSet().apply {
//                playSequentially(email, pass)
//                start()
//            }
//        }
//    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        exitProcess(0)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val DURATION: Long = 200
    }
}