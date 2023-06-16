package com.example.mobile_dev

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.text.format.DateFormat
import com.example.mobile_dev.ui.agreement.DataAgreement
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
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}
fun uriToFile(selectedImage: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(2048)
    var len: Int

    while(inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun createPDF(data: ArrayList<DataAgreement>, signature: Bitmap?, context: Context) {
    val pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
    val file = File(pdfpath, "agreement.pdf")
    val writer = PdfWriter(file)
    val pdfDocument = PdfDocument(writer)
    val document = Document(pdfDocument)
    pdfDocument.defaultPageSize = PageSize.A4
    document.setMargins(44f, 44f, 44f, 44f)

    val bitmap = BitmapFactory.decodeFile(data[0].photo?.path)
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
    val title = Paragraph(context.getString(R.string.title)).setBold().setFontSize(24f).setTextAlignment(
        TextAlignment.CENTER)
    val suratnum = Paragraph(context.getString(R.string.suratnum) + randomStringByKotlinRandom() +"/"+ randomNumByKotlinRandom() +"/2023").setFontSize(18f).setTextAlignment(
        TextAlignment.CENTER)
    val opening = Paragraph(context.getString(R.string.opening) ).setFontSize(14f).setTextAlignment(
        TextAlignment.LEFT)
    val opening2 = Paragraph(context.getString(R.string.opening2) ).setTextAlignment(TextAlignment.LEFT).setFontSize(14f)
    val closing = Paragraph(context.getString(R.string.closing)).setFontSize(14f).setTextAlignment(
        TextAlignment.JUSTIFIED)
    val closing2 = Paragraph(context.getString(R.string.town) + date).setFontSize(16f).setTextAlignment(
        TextAlignment.RIGHT)
    val closing3 = Paragraph("\n\n\n ${data[0]}").setFontSize(16f).setTextAlignment(TextAlignment.RIGHT)
    val width = floatArrayOf(200f, 200f)
    val table = Table(width)
    table.setHorizontalAlignment(HorizontalAlignment.CENTER)
    table.addCell(Cell().add(Paragraph("Name")))
    table.addCell(Cell().add(Paragraph(data[0].name)))
    table.addCell(Cell().add(Paragraph("Place & Date Birth")))
    table.addCell(Cell().add(Paragraph(data[0].place + data[0].date)))
    table.addCell(Cell().add(Paragraph("Email")))
    table.addCell(Cell().add(Paragraph(data[0].email)))
    table.addCell(Cell().add(Paragraph("Telephone")))
    table.addCell(Cell().add(Paragraph(data[0].telf)))
    table.addCell(Cell().add(Paragraph("Location")))
    table.addCell(Cell().add(Paragraph(data[0].loc)))
    document.add(title)
    document.add(suratnum)
    document.add(opening)
    document.add(opening2)
    document.add(table)
    document.add(closing)
    document.add(closing2)
    if(signature != null) {
        val streamSign = ByteArrayOutputStream()
        signature.compress(Bitmap.CompressFormat.JPEG, 10, streamSign)
        val bitmapDataSign = stream.toByteArray()
        val imageDataSign: ImageData = ImageDataFactory.create(bitmapDataSign)
        val imageSign = Image(imageDataSign)
        document.add(imageSign)
    }
    document.add(closing3)
    document.add(image)
    document.close()
}