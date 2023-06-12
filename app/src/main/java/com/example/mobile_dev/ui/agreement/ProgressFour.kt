package com.example.mobile_dev.ui.agreement

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.databinding.ActivityProgressFourBinding
import com.example.mobile_dev.databinding.DialogchooseBinding
import com.example.mobile_dev.ui.auth.dataStore
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.detail.DetailActivity
import com.example.mobile_dev.ui.theme.MobiledevTheme
import com.example.mobile_dev.uriToFile
import java.io.File

class ProgressFour : AppCompatActivity() {
    private lateinit var bindingDialog: DialogchooseBinding
    private lateinit var viewModel: SettingViewModel
    private lateinit var dialog: Dialog
    private var getFile: File? = null
    private var isPhoto = false
    private val agreementViewModel: AgreementViewModel by viewModels {
        ViewModelFactory(this)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == ProgressThree.cameraX) {
            isPhoto = true
            val myFile = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra(ProgressThree.id, File::class.java)
            } else {
                it.data?.getSerializableExtra(ProgressThree.id)
            } as File
            val isBackCamera = it.data?.getBooleanExtra(ProgressThree.camera, true) as Boolean
            myFile.let { file ->
                getFile = file
                agreementViewModel.setFile(file)
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val selectedImage = it.data?.data as Uri
            selectedImage.let { result ->
                val myFile = uriToFile(result, this@ProgressFour)
                agreementViewModel.setFile(myFile)
                binding.photo.setImageURI(result)
            }
        }
    }
    private lateinit var binding: ActivityProgressFourBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressFourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this,
               REQUIRED_PERMISSION,
               REQUEST_CODE
            )
        }

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true,
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@ProgressFour, ProgressThree::class.java)
                        startActivity(i)
                    }
                )
            }
        }
        val pref = UserPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]
        agreementViewModel.tempFile.observe(this@ProgressFour) { file ->
            val bitmap = BitmapFactory.decodeFile(file.path)
            binding.photo.setImageBitmap(
                Bitmap.createBitmap(
                    bitmap,
                    bitmap.width/2 - bitmap.height/4,
                    0,
                    bitmap.height - bitmap.height/2,
                    bitmap.height
                ))
            isPhoto = true
        }
        binding.takePhoto.setOnClickListener{
            isPhoto = true
            addStory()
        }
        binding.nextBtn.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.next),
                    onClick = {
                        if(isPhoto) {
                            val i = Intent(this@ProgressFour, DetailActivity::class.java)
                            startActivity(i)
                        } else {
                            Toast.makeText(this@ProgressFour, resources.getString(R.string.errorphoto), Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
    private fun addStory() {
        dialog = Dialog(this)
        bindingDialog = DialogchooseBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()
        bindingDialog.camera.setOnClickListener {
            dialog.cancel()
            startCameraX()
        }

        bindingDialog.gallery.setOnClickListener {
            dialog.cancel()
            startGallery()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, resources.getString(R.string.camera))
        launcherIntentGallery.launch(chooser)
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

    private fun startCameraX() {
        isPhoto = true
        val i = Intent(this, CameraActivity2::class.java)
        launcherIntentCameraX.launch(i)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val id = "picture"
        const val camera = "isBackCamera"
        const val cameraX = 200
        private val REQUIRED_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE = 20
    }
}