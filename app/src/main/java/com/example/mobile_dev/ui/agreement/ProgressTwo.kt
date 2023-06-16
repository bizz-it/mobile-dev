package com.example.mobile_dev.ui.agreement

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.createPDF
import com.example.mobile_dev.databinding.ActivityProgressTwoBinding
import com.example.mobile_dev.ui.auth.dataStore
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.File

class ProgressTwo : AppCompatActivity() {
    private lateinit var data: ArrayList<DataAgreement>
    private lateinit var binding: ActivityProgressTwoBinding
    private var photo2: File? = null
    private var signatureBitmap: Bitmap? = null
    private var token: String? = null
    private lateinit var viewModel: SettingViewModel
    private val agreementViewModel: AgreementViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val file = File(Environment.getExternalStorageDirectory().path + "/Documents/agreement.pdf")
        binding.pdfView.fromFile(file).swipeHorizontal(true).enableDoubletap(true)
            .defaultPage(0)
            .spacing(2)
            .load()
        val pref = UserPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]

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
                        createPDF(data, signatureBitmap, this@ProgressTwo)
                        agreementViewModel.agreement(data)
                        val i = Intent(this@ProgressTwo, ProgressThree::class.java)
                        startActivity(i)
                    }
                )
            }
        }
        binding.save.isEnabled = false
        binding.refresh.isEnabled = false
        binding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() { }

            override fun onSigned() {
                binding.save.isEnabled = true
                binding.refresh.isEnabled = true
            }

            override fun onClear() {
                binding.save.isEnabled = false
                binding.refresh.isEnabled = false
            }
        })

        binding.refresh.setOnClickListener {
            binding.signaturePad.clear()
        }

        binding.save.setOnClickListener {
            signatureBitmap = binding.signaturePad.signatureBitmap
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.permit),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE = 20
    }
}