package com.example.mobile_dev.ui.agreement

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.R
import com.example.mobile_dev.SettingFactory
import com.example.mobile_dev.SettingViewModel
import com.example.mobile_dev.UserPreferences
import com.example.mobile_dev.createCustomTempFile
import com.example.mobile_dev.databinding.ActivityProgressThreeBinding
import com.example.mobile_dev.ui.auth.dataStore
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme
import java.io.File

class ProgressThree : AppCompatActivity() {
    private lateinit var binding: ActivityProgressThreeBinding
    private lateinit var currentPhotoPath: String
    private lateinit var viewModel: SettingViewModel
    private lateinit var dialog: Dialog

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            var bitmap = BitmapFactory.decodeFile(myFile.path)
//            val koefX = bitmap.width
//            val koefY = bitmap.height
//            mainViewModel.setFile(myFile)
            binding.photo.setImageBitmap(Bitmap.createBitmap(bitmap, 200,200, 360,360))
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val selectedImage = it.data?.data as Uri
            selectedImage.let { result ->
//                val myFile = uriToFile(result, this@AddStoryActivity)
//                mainViewModel.setFile(myFile)
                binding.photo.setImageURI(result)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, REQUEST_CODE)
        }

        val pref = UserPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]
        binding.apply {
//            mainViewModel.tempFile.observe(this@AddStoryActivity) { file ->
//                val bitmap = BitmapFactory.decodeFile(file.path)
//
//                val koefX = bitmap.width/5
//                val koefY = bitmap.height/2
//                photo.setImageBitmap(Bitmap.createBitmap(bitmap, koefX, koefY, 1860,1212))
//            }
            composeView.setContent {
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

//            if(currentPhotoPath == null) {
                nextBtn.setContent {
                    MobiledevTheme {
                        ButtonApp(
                            getString(R.string.take),
                            onClick = {
                                startTakePhoto()
                            }
                        )
                    }
                }
//            } else {
//                nextBtn.setContent {
//                    MobiledevTheme {
//                        ButtonApp(
//                            getString(R.string.next),
//                            onClick = {
//                                val i = Intent(this@ProgressThree, ProgressFour::class.java)
//                                startActivity(i)
//                            }
//                        )
//                    }
//                }
//            }
        }
    }

    private fun addStory(story: String, image: File) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading)
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }
//        viewModel.getThemeSettings().observe(this@AddStoryActivity) { user ->
//            mainViewModel.postStory(user.token, story, image, lat, lon).observe(this) { result ->
//                dialog.apply {
//                    if (result != null) {
//                        when (result) {
//                            is Result.Loading -> {
//                                show()
//                            }
//                            is Result.Success -> {
//                                cancel()
//                                Toast.makeText(
//                                    this@AddStoryActivity,
//                                    resources.getString(R.string.upsuccess),
//                                    Toast.LENGTH_LONG
//                                )
//                                    .show()
//                                finish()
//                            }
//                            is Result.Error -> {
//                                cancel()
//                                Toast.makeText(
//                                    this@AddStoryActivity,
//                                    result.error,
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, resources.getString(R.string.camera))
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ProgressThree,
                "franchise",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
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

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE = 20
    }
}