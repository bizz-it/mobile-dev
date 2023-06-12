package com.example.mobile_dev.ui.agreement

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProgressTwoBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class ProgressTwo : AppCompatActivity() {

    private lateinit var binding: ActivityProgressTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
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
        binding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                //Toast.makeText(this@MainActivity, "Mulai menulis...", Toast.LENGTH_SHORT).show()
            }

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
            val signatureBitmap = binding.signaturePad.signatureBitmap
            if (addJpgSignatureToGallery(signatureBitmap)) {
                Toast.makeText(this@ProgressTwo, "Tanda tangan disimpan ke dalam Galeri",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ProgressTwo, "Tidak dapat menyimpan Tanda Tangan",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

        fun getAlbumStorageDir(albumName: String?): File {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName)
            if (!file.mkdirs()) {
                Log.e("SignaturePad", "Directory not created")
            }
            return file
        }

        //Image Signature
        @Throws(IOException::class)
        fun saveBitmapToJPG(bitmap: Bitmap, photo: File?) {
            val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(newBitmap)
            canvas.drawColor(Color.WHITE)
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            val stream: OutputStream = FileOutputStream(photo)
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            stream.close()
        }

    fun addJpgSignatureToGallery(signature: Bitmap): Boolean {
        var result = false
        try {
            val photo = File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()))
            saveBitmapToJPG(signature, photo)
            scanMediaFile(photo)
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private fun scanMediaFile(photo: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(photo)
        mediaScanIntent.data = contentUri
        this@ProgressTwo.sendBroadcast(mediaScanIntent)
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
                    resources.getString(com.example.mobile_dev.R.string.permit),
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
        private const val STORAGE_CODE = 1000
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE = 20
    }
}