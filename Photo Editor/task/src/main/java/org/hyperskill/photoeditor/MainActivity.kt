package org.hyperskill.photoeditor

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ColorMatrix
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.graphics.set
import androidx.core.view.drawToBitmap
import com.google.android.material.slider.Slider


class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnGallery: Button
    private lateinit var btnRestore: Button
    private lateinit var slBrightness : Slider
    private lateinit var originalBitmap : Bitmap


    private val activityResultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoUri = result.data?.data ?: return@registerForActivityResult
                imageView.setImageURI(photoUri)
                originalBitmap = imageView.drawToBitmap()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()

        //do not change this line
        imageView.setImageBitmap(createBitmap())

        btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
        }

        slBrightness.addOnChangeListener { slider, value, fromUser ->
            imageView.setImageBitmap(applyBrightness(value.toInt()))
        }

        btnRestore.setOnClickListener {
            imageView.setImageBitmap(originalBitmap)
        }

    }

    private fun applyBrightness(value: Int): Bitmap {

        //new bitmap must be equal to original
        var newBitmap = originalBitmap.copy(originalBitmap.config, true)

        for (x in 0 until newBitmap.width){
            for (y in 0 until newBitmap.height){

                val color = newBitmap.getPixel(x,y)

                var R = color.red + value
                if (R > 255) { R = 255 }
                else if (R < 0) { R = 0}

                var G = color.green + value
                if (G > 255) { G = 255 }
                else if (G < 0) { G = 0}

                var B = color.blue + value
                if (B > 255) { B = 255 }
                else if (B < 0) { B = 0}

                newBitmap.setPixel(x, y, Color.rgb(R, G, B))
            }
        }

        return newBitmap

    }

    private fun bindViews() {
        imageView = findViewById(R.id.ivPhoto)
        btnGallery = findViewById(R.id.btnGallery)
        btnRestore = findViewById(R.id.btnRestore)
        slBrightness = findViewById(R.id.slBrightness)
    }

    // do not change this function
    fun createBitmap(): Bitmap {
        val width = 200
        val height = 100
        val pixels = IntArray(width * height)
        // get pixel array from source

        var R: Int
        var G: Int
        var B: Int
        var index: Int

        for (y in 0 until height) {
            for (x in 0 until width) {
                // get current index in 2D-matrix
                index = y * width + x
                // get color
                R = x % 100 + 40
                G = y % 100 + 80
                B = (x+y) % 100 + 120

                pixels[index] = Color.rgb(R,G,B)

            }
        }
        // output bitmap
        val bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height)
        originalBitmap = bitmapOut
        return bitmapOut
    }
}