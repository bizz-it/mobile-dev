package com.example.mobile_dev.ui.agreement

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import com.example.mobile_dev.databinding.ActivityProgressOneBinding
import com.example.mobile_dev.databinding.DialogchooseBinding
import com.example.mobile_dev.ui.auth.dataStore
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.CamButton
import com.example.mobile_dev.ui.component.PriceList
import com.example.mobile_dev.ui.component.RadioCostum
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.detail.DetailActivity
import com.example.mobile_dev.ui.theme.MobiledevTheme
import com.example.mobile_dev.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Locale

class ProgressOne : AppCompatActivity() {

    private lateinit var binding: ActivityProgressOneBinding
    private lateinit var bindingDialog: DialogchooseBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: SettingViewModel
    private lateinit var dialog: Dialog
    private lateinit var currentPhotoPath: String
    private val agreementViewModel: AgreementViewModel by viewModels {
        ViewModelFactory(this)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val bitmap = BitmapFactory.decodeFile(myFile.path)
            agreementViewModel.setFile(myFile)
            binding.photo.setImageBitmap(bitmap)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val selectedImage = it.data?.data as Uri
            selectedImage.let { result ->
                val myFile = uriToFile(result, this@ProgressOne)
                agreementViewModel.setFile(myFile)
                binding.photo.setImageURI(result)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]

        if(!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, REQUEST_CODE)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@ProgressOne, DetailActivity::class.java)
                        startActivity(i)
                    }
                )
            }
        }
        agreementViewModel.tempFile.observe(this@ProgressOne) { file ->
            val bitmap = BitmapFactory.decodeFile(file.path)
            binding.photo.setImageBitmap(bitmap)
        }

        binding.apply {
                viewModel.getUserData().observe(this@ProgressOne) { user ->
                    emailInput.setText(user.email.toString())
                    nameInput.setText(user.nama.toString())
                    telfInput.setText(user.noTelp.toString())
                    if(user.tanggalLahir?.equals("null") == true) {
                        dateInput.setText(user.tanggalLahir.toString())
                    } else if(user.tempatLahir?.equals("null") == true) {
                        placeInput.setText(user.tempatLahir.toString())
                    }
                }
        }


        binding.locbtn.setOnClickListener {
            getMyLastLocation()
            agreementViewModel.latlng.observe(this@ProgressOne) { file ->
                Log.d("PHOTO", file?.get(0)?.getAddressLine(0).toString() )
                binding.locInput.setText(file?.get(0)?.getAddressLine(0).toString())
                file?.get(0)?.getAddressLine(0)
            }
        }

        binding.camBtn.setContent {
            MobiledevTheme {
                CamButton(
                    getString(R.string.add),
                    onClick = {
                        addStory()
                    }
                )
            }
        }

        binding.nextBtn.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.next),
                    onClick = {
                        createPdf()
                        val i = Intent(this@ProgressOne, ProgressTwo::class.java)
                        startActivity(i)
                    }
                )
            }
        }

        binding.radiogroup.setContent {
            MobiledevTheme {
                val priceList = arrayListOf<PriceList>()
                priceList.add(
                    PriceList(
                        name = "Prince",
                        price = "250"
                    )
                )
                priceList.add(
                    PriceList(
                        name = "Lucky",
                        price = "500"
                    )
                )
                priceList.add(
                    PriceList(
                        name = "Frankie",
                        price = "300"
                    )
                )
                RadioCostum(
                    priceList
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
            startTakePhoto()
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

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ProgressOne,
                "franchise",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val requestLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLastLocation()
            }
        }
    private fun getMyLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    agreementViewModel.setLatLng(geocoder.getFromLocation(location.latitude, location.longitude, 1))
                }
            }
        } else {
            requestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun createPdf() {
        agreementViewModel.tempFile.observe(this@ProgressOne) { photo ->
            agreementViewModel.latlng.observe(this@ProgressOne) { loc ->
                viewModel.getUserData().observe(this@ProgressOne) { user ->
                    val pdfPath = Environment.getExternalStorageDirectory().toString()
                    val file = File(pdfPath, "myPDF.pdf")
                    val outputStream: OutputStream = FileOutputStream(file)
                    val writer = PdfWriter(file)
                    val pdfDocument = PdfDocument(writer)
                    val document = Document(pdfDocument)
                    pdfDocument.defaultPageSize = PageSize.A4
                    document.setMargins(0f, 0f, 0f, 0f)

                    val bitmap = BitmapFactory.decodeFile(photo.path)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream)
                    val bitmapData = stream.toByteArray()
                    val imageData: ImageData = ImageDataFactory.create(bitmapData)
                    val image = Image(imageData)

                    val visitorTicket = Paragraph("Visitor Ticket").setBold().setFontSize(24f)
                        .setTextAlignment(TextAlignment.CENTER)
                    val group =
                        Paragraph("Tourism Department\n" + "Government of Uttar Pradesh, India").setTextAlignment(
                            TextAlignment.CENTER
                        ).setFontSize(12f)
                    val varansi = Paragraph("Varanasi").setBold().setFontSize(20f)
                        .setTextAlignment(TextAlignment.CENTER)
                    val width = floatArrayOf(100f, 100f)
                    var table = Table(width)
                    table.setHorizontalAlignment(HorizontalAlignment.CENTER)
                    table.addCell(Cell().add(Paragraph("Name")))
                    table.addCell(Cell().add(Paragraph(user.nama.toString())))
                    table.addCell(Cell().add(Paragraph("Email")))
                    table.addCell(Cell().add(Paragraph(user.email.toString())))
                    table.addCell(Cell().add(Paragraph("Telephone Input")))
                    table.addCell(Cell().add(Paragraph(user.noTelp.toString())))
                    table.addCell(Cell().add(Paragraph("Place & Date Birth")))
                    table.addCell(Cell().add(Paragraph("${user.tempatLahir.toString()} + ${user.tanggalLahir.toString()}")))
                    table.addCell(Cell().add(Paragraph("Location")))
                    table.addCell(Cell().add(Paragraph(loc?.get(0)?.getAddressLine(0))))

                    document.add(visitorTicket)
                    document.add(group)
                    document.add(varansi)
                    document.add(table)
                    document.add(image)
                    document.close()
                }
            }
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