package com.example.mobile_dev.ui.screen.scan

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

private lateinit var imageCapture: ImageCapture
private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
var isFlash = true

@Composable
//fun ScanScreen() {
//    val context = LocalContext.current
//    val lifecycle = LocalLifecycleOwner.current
//
//    AndroidViewBinding(ActivityCameraBinding::inflate) {
//        imageCapture = ImageCapture.Builder()
//            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
//            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
//            .build()
//
//        this.captureImage.setOnClickListener {
//            val imageCapture = imageCapture
//            val photoFile = createFile(.)
//            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//            imageCapture.takePicture(
//                outputOptions,
//                ContextCompat.getMainExecutor(this),
//                object : ImageCapture.OnImageSavedCallback {
//                    override fun onError(exc: ImageCaptureException) {
//                        Toast.makeText(
//                            this@CameraActivity,
//                            "Gagal mengambil gambar.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            )
//        }
//        this.switchCamera.setOnClickListener {
//            cameraSelector =
//                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
//            val cameraProviderFeature = ProcessCameraProvider.getInstance(context)
//            cameraProviderFeature.addListener({
//                val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
//                val preview = Preview.Builder().build().also {
//                    it.setSurfaceProvider(this.viewFinder.surfaceProvider)
//                }
//                imageCapture = ImageCapture.Builder().build()
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(lifecycle, cameraSelector, preview, imageCapture)
//            }, ContextCompat.getMainExecutor(context))
//        }
//        this.flashCamera.setOnClickListener {
//            if (isFlash) {
//                imageCapture.flashMode = ImageCapture.FLASH_MODE_ON
//                this.flashCamera.setImageDrawable(
//                    AppCompatResources.getDrawable(
//                        context,
//                        R.drawable.round_flash_off_24
//                    )
//                )
//                isFlash = false
//            } else {
//                imageCapture.flashMode = ImageCapture.FLASH_MODE_OFF
//                this.flashCamera.setImageDrawable(
//                    AppCompatResources.getDrawable(
//                        context,
//                        R.drawable.round_flash_on_24
//                    )
//                )
//                isFlash = true
//            }
//        }
//    }
//}

fun ScanScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview
                )
            }, executor)
            previewView
        },
        modifier = Modifier.fillMaxSize(),
    )
}