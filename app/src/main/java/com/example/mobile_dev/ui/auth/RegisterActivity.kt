package com.example.mobile_dev.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityRegisterBinding
import com.example.mobile_dev.data.Result

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
        playAnimation()

        binding.login.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i, ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegisterActivity).toBundle())
        }

        binding.apply{
            btnRegist.setOnClickListener{
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
                        passInput.error = resources.getString(R.string.errormail)
                    }
                    telf.isEmpty() -> {
                        passInput.error = resources.getString(R.string.emptytelf)
                    }
                    secPass.isEmpty() -> {
                        passInput.error = resources.getString(R.string.emptypass)
                    }
                    secPass != (pass) -> {
                        passInput.error = resources.getString(R.string.errorsecpass)
                    }
                    else -> { register(name, email, pass, telf) }
                }
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
        val i = Intent(this, LoginActivity::class.java)

        authViewModel.register(name, email, pass, telf).observe(this) { result ->
            if (result != null) {
                dialog.apply {
                    when (result) {
                        is Result.Loading -> {
                            show()
                        }
                        is Result.Success -> {
                            cancel()
                                if(binding.passInput.text?.length!! > 7) {
                                    Toast.makeText(this@RegisterActivity, resources.getString(R.string.regist), Toast.LENGTH_SHORT).show()
                                    startActivity(i)
                                } else {
                                    cancel()
                                    Toast.makeText(this@RegisterActivity, resources.getString(R.string.errorpass), Toast.LENGTH_LONG).show()
                                }
                        }
                        is Result.Error -> {
                            cancel()
                            Toast.makeText(this@RegisterActivity, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun playAnimation() {
        binding.apply {
            val secpass = ObjectAnimator.ofFloat(secPass, View.ALPHA, 1f).setDuration(DURATION)
            val pass = ObjectAnimator.ofFloat(pass, View.ALPHA, 1f).setDuration(DURATION)
            val telf = ObjectAnimator.ofFloat(telf, View.ALPHA, 1f).setDuration(DURATION)
            val email = ObjectAnimator.ofFloat(email, View.ALPHA, 1f).setDuration(DURATION)
            val name = ObjectAnimator.ofFloat(name, View.ALPHA, 1f).setDuration(DURATION)

            AnimatorSet().apply {
                playSequentially(name, email, telf, pass, secpass)
                start()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val DURATION: Long = 200
    }
}