package com.example.mobile_dev.ui.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R
import com.example.mobile_dev.data.Result
import com.example.mobile_dev.databinding.ActivityRegisterBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.theme.MobiledevTheme

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        playAnimation()

        binding.login.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnRegist.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.regist),
                    onClick = { binding.apply{
                            val name = nameInput.text.toString()
                            val email = emailInput.text.toString()
                            val telf = telfInput.text.toString()
                            val pass = passInput.text.toString()
                            val secPass = secPassInput.text.toString()
                            when {
                                name.isEmpty() -> {
                                    nameInput.error = resources.getString(R.string.emptyname)
                                }
                                email.isEmpty() -> {
                                    emailInput.error = resources.getString(R.string.emptymail)
                                }
                                pass.isEmpty() -> {
                                    passInput.error = resources.getString(R.string.emptypass)
                                }
                                pass.length < 6 -> {
                                    passInput.error = resources.getString(R.string.errorpass)
                                }
                                !email.contains('@') -> {
                                    emailInput.error = resources.getString(R.string.errormail)
                                }
                                telf.isEmpty() -> {
                                    telfInput.error = resources.getString(R.string.emptytelf)
                                }
                                secPass.isEmpty() -> {
                                    secPassInput.error = resources.getString(R.string.emptypass)
                                }
                                secPass != (pass) -> {
                                    secPassInput.error = resources.getString(R.string.errorsecpass)
                                }
                                !checkbox.isChecked -> {
                                    Toast.makeText(this@RegisterActivity, R.string.check, Toast.LENGTH_SHORT).show()
                                }
                                else -> { register(name, email, pass, telf) }
                            }
                    } }
                )
            }
        }
    }

    private fun register(name: String, email: String, pass: String, telf: String) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        authViewModel.register(name, email, pass, telf).observe(this) { result ->
            if (result != null) {
                dialog.apply {
                    when (result) {
                        is Result.Loading -> {
                            show()
                        }
                        is Result.Success -> {
                            cancel()
                            val i = Intent(this@RegisterActivity, OTPRegister::class.java)
                            Toast.makeText(this@RegisterActivity, resources.getString(R.string.successreg), Toast.LENGTH_SHORT).show()
                            startActivity(i)
                        }
                        is Result.Error -> {
                            cancel()
                            Toast.makeText(this@RegisterActivity, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
//    private fun playAnimation() {
//        binding.apply {
//            val secpass = ObjectAnimator.ofFloat(secPass, View.ALPHA, 1f).setDuration(DURATION)
//            val pass = ObjectAnimator.ofFloat(pass, View.ALPHA, 1f).setDuration(DURATION)
//            val telf = ObjectAnimator.ofFloat(telf, View.ALPHA, 1f).setDuration(DURATION)
//            val email = ObjectAnimator.ofFloat(email, View.ALPHA, 1f).setDuration(DURATION)
//            val name = ObjectAnimator.ofFloat(name, View.ALPHA, 1f).setDuration(DURATION)
//
//            AnimatorSet().apply {
//                playSequentially(name, email, telf, pass, secpass)
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