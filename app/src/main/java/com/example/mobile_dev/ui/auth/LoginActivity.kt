package com.example.mobile_dev.ui.auth

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.data.Result
import com.example.mobile_dev.databinding.ActivityLoginBinding
import com.example.mobile_dev.ui.agreement.ProgressOne
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.theme.MobiledevTheme

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

        binding.btnLogin.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.login),
                    onClick = { binding.apply {
                        val email = emailInput.text.toString()
                        val password = passInput.text.toString()
                        when {
                            email.isEmpty() -> {
                                emailInput.error = resources.getString(R.string.emptymail)
                            }
                            !email.contains('@') -> {
                                emailInput.error = resources.getString(R.string.errormail)
                            }
                            password.isEmpty() -> {
                                pass.error = resources.getString(R.string.emptypass)
                                pass.errorIconDrawable = null
                            }
                            password.length < 6 -> {
                                passInput.error = resources.getString(R.string.errorpass)
                                pass.errorIconDrawable = null
                            }
                            else -> { login(email, password)}
                        }
                    } }
                )
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
                            val i = Intent(this@LoginActivity, ProgressOne::class.java)
                            viewModel.saveUserData(result.data)
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val DURATION: Long = 200
    }
}