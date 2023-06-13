package com.example.mobile_dev.ui.agreement

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateFormat
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
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ProgressOne : AppCompatActivity() {

    private lateinit var binding: ActivityProgressOneBinding
    private lateinit var bindingDialog: DialogchooseBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: SettingViewModel
    private lateinit var dialog: Dialog
    private lateinit var data: ArrayList<String>
    private lateinit var currentPhotoPath: String
    private val agreementViewModel: AgreementViewModel by viewModels {
        ViewModelFactory(this)
    }
    private var photo2: File? = null
    private val cal: Calendar = Calendar.getInstance()

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val bitmap = BitmapFactory.decodeFile(myFile.path)
            agreementViewModel.setFile(myFile)
            photo2 = myFile
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
        data = ArrayList()
        super.onCreate(savedInstanceState)
        binding = ActivityProgressOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]
        val cal = Calendar.getInstance()
        if(!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, REQUEST_CODE)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.apply {
            composeView.setContent {
                MobiledevTheme {
                    TopBar(true,
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
                photo.setImageBitmap(bitmap)
            }

            viewModel.getUserData().observe(this@ProgressOne) { user ->
                emailInput.setText(user.email.toString())
                nameInput.setText(user.nama.toString())
                telfInput.setText(user.noTelp.toString())
                if (user.tanggalLahir?.equals("null") != true) {
                    dateInput.setText(user.tanggalLahir.toString())
                }
                if (user.tempatLahir?.equals("null") != true) {
                    placeInput.setText(user.tempatLahir.toString())
                }
            }
            locbtn.setOnClickListener {
                getMyLastLocation()
                agreementViewModel.latlng.observe(this@ProgressOne) { file ->
                    Log.d("PHOTO", file?.get(0)?.getAddressLine(0).toString())
                    locInput.setText(file?.get(0)?.getAddressLine(0).toString())
                }
            }

            camBtn.setContent {
                MobiledevTheme {
                    CamButton(
                        getString(R.string.add),
                        onClick = {
                            addStory()
                        }
                    )
                }
            }

            nextBtn.setContent {
                MobiledevTheme {
                    ButtonApp(
                        getString(R.string.next),
                        onClick = {
                            val email = emailInput.text.toString()
                            val name = nameInput.text.toString()
                            val telf = telfInput.text.toString()
                            val place = placeInput.text.toString()
                            val date = dateInput.text.toString()
                            val loc = locInput.text.toString()
                            if (email.isNotEmpty() && name.isNotEmpty() && telf.isNotEmpty() && place.isNotEmpty() && date.isNotEmpty() && loc.isNotEmpty() && photo2 != null) {
                                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED
                                ) {
                                    val permissions =
                                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    requestPermissions(permissions, STORAGE_CODE)
                                } else {
                                    data.add(name)
                                    data.add(email)
                                    data.add(telf)
                                    data.add(place)
                                    data.add(date)
                                    data.add(loc)
                                    createPdf(data, photo2)
                                    val i = Intent(this@ProgressOne, ProgressTwo::class.java)
                                    startActivity(i)
                                }
                            }
                            else {
                                Toast.makeText(this@ProgressOne, "Please complete all your information first", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateInView()
                }

//            datebtn.setOnClickListener {
//                DatePickerDialog(
//                    this@ProgressOne,
//                    dateSetListener,
//                    cal.get(Calendar.YEAR),
//                    cal.get(Calendar.MONTH),
//                    cal.get(Calendar.DAY_OF_MONTH)
//                ).show()
//            }

            radiogroup.setContent {
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
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateInput.setText(sdf.format(cal.time))
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

    private fun createPdf(data: ArrayList<String>, photo: File?) {
        val pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
        val file = File(pdfpath, "agreement.pdf")
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(44f, 44f, 44f, 44f)

        val bitmap = BitmapFactory.decodeFile(photo?.path)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        val bitmapData = stream.toByteArray()
        val imageData: ImageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)

        val charPool : List<Char> = ('A'..'M') + ('A'..'Z')
        val numPool : List<Char> = ('0'..'5') + ('6'..'9')

        fun randomStringByKotlinRandom() = (1..4)
            .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
            .joinToString("")
        fun randomNumByKotlinRandom() = (1..4)
            .map { Random.nextInt(0, numPool.size).let { charPool[it] } }
            .joinToString("")
        val date : String = DateFormat.format("dd-MMM-yyyy" , Date()) as String
        val title = Paragraph("SURAT PERJANJIAN WARALABA MIXUE").setBold().setFontSize(24f).setTextAlignment(TextAlignment.CENTER)
        val nosurat = Paragraph("Nomor : " + randomStringByKotlinRandom() +"/"+ randomNumByKotlinRandom() +"/2023").setFontSize(68f).setTextAlignment(TextAlignment.CENTER)
        val opening = Paragraph("Dengan hormat,").setFontSize(14f).setTextAlignment(TextAlignment.LEFT)
        val opening2 = Paragraph("Pada hari ini, telah dibuat dan ditanda tangani penawaran perjanjian waralaba Mixue. Yang bertanda tangan dibawah ini:\n\n").setTextAlignment(TextAlignment.LEFT).setFontSize(14f)
        val closing = Paragraph("\nDalam hal ini bertindak sebagai pengaju untuk melakukan perjanjian dalam rangka mengakuisisi atau membeli waralaba yang ditawarkan. Demikianlah perjanjian ini dibuat dan ditandatangani oleh para pihak dalam keadaan sehat jasmani dan rohani tanpa adanya paksaan dari pihak manapun.\n").setFontSize(14f).setTextAlignment(TextAlignment.JUSTIFIED)
        val closing2 = Paragraph("\n\nMalang, $date").setFontSize(16f).setTextAlignment(TextAlignment.RIGHT)
        val closing3 = Paragraph("\n\n\n ${data[0]}").setFontSize(16f).setTextAlignment(TextAlignment.RIGHT)
        val width = floatArrayOf(200f, 200f)
        val table = Table(width)
        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Name")))
        table.addCell(Cell().add(Paragraph(data[0])))
        table.addCell(Cell().add(Paragraph("Place & Date Birth")))
        table.addCell(Cell().add(Paragraph(data[3]+ data[4])))
        table.addCell(Cell().add(Paragraph("Email")))
        table.addCell(Cell().add(Paragraph(data[1])))
        table.addCell(Cell().add(Paragraph("Telephone")))
        table.addCell(Cell().add(Paragraph(data[2])))
        table.addCell(Cell().add(Paragraph("Location")))
        table.addCell(Cell().add(Paragraph(data[5])))
        document.add(title)
        document.add(nosurat)
        document.add(opening)
        document.add(opening2)
        document.add(table)
        document.add(closing)
        document.add(closing2)
        document.add(closing3)
        document.add(image)
        document.close()
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
        private const val STORAGE_CODE = 1000
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE = 20
    }
}