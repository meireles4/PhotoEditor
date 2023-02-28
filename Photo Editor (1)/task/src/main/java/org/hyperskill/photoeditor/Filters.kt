package org.hyperskill.photoeditor

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.pow

class Filters {

    fun applyBrightness(value: Int, bitmap: Bitmap): Bitmap {

        var newBitmap = bitmap

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

    fun calcAvgBrightness(bitmap: Bitmap): Int {
        var accumulator: Long = 0

        for (i in 0 until bitmap.width) {
            for (j in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(i, j)
                accumulator += pixel.red + pixel.green + pixel.blue
            }
        }

        //calculate average and return
        return (accumulator / (bitmap.width * bitmap.height * 3)).toInt()
    }

    fun applyContrast(contrastValue: Int, avgBright: Int, bitmap: Bitmap): Bitmap {

        val contrastDouble: Double = contrastValue.toDouble()
        val alpha: Double = (255 + contrastDouble) / (255 - contrastDouble)
        Log.d("Contrast", "contrastDouble: $contrastDouble; alpha: $alpha")

        for (x in 0 until bitmap.width){
            for (y in 0 until bitmap.height){

                val color = bitmap.getPixel(x,y)

                var R = (alpha * (color.red - avgBright) + avgBright).toInt()
                if (R > 255) { R = 255 }
                else if (R < 0) { R = 0}

                var G = (alpha * (color.green - avgBright) + avgBright).toInt()
                if (G > 255) { G = 255 }
                else if (G < 0) { G = 0}

                var B = (alpha * (color.blue - avgBright) + avgBright).toInt()
                if (B > 255) { B = 255 }
                else if (B < 0) { B = 0}

                bitmap.setPixel(x, y, Color.rgb(R, G, B))
            }
        }

        return bitmap
    }

    fun applySaturation(saturationValue: Int, bitmap: Bitmap): Bitmap {

        val saturationDouble: Double = saturationValue.toDouble()
        val alpha: Double = (255 + saturationDouble) / (255 - saturationDouble)
        Log.d("Contrast", "contrastDouble: $saturationDouble; alpha: $alpha")

        for (x in 0 until bitmap.width){
            for (y in 0 until bitmap.height){

                val color = bitmap.getPixel(x,y)

                val rgbAvg = (color.red + color.green + color.blue) / 3

                var R = (alpha * (color.red - rgbAvg) + rgbAvg).toInt()
                if (R > 255) { R = 255 }
                else if (R < 0) { R = 0}

                var G = (alpha * (color.green - rgbAvg) + rgbAvg).toInt()
                if (G > 255) { G = 255 }
                else if (G < 0) { G = 0}

                var B = (alpha * (color.blue - rgbAvg) + rgbAvg).toInt()
                if (B > 255) { B = 255 }
                else if (B < 0) { B = 0}

                bitmap.setPixel(x, y, Color.rgb(R, G, B))
            }
        }

        return bitmap
    }

    fun applyGamma(gammaValue: Float, bitmap: Bitmap): Bitmap {

        for (x in 0 until bitmap.width){
            for (y in 0 until bitmap.height){

                val color = bitmap.getPixel(x,y)

                var R = (255 * (color.red.toDouble() / 255).pow(gammaValue.toDouble())).toInt()
                if (R > 255) { R = 255 }
                else if (R < 0) { R = 0}

                //var G = (255 * (color.green % 255).toDouble().pow(gamaDouble)).toInt()
                var G = (255 * (color.green.toDouble() / 255).pow(gammaValue.toDouble())).toInt()
                if (G > 255) { G = 255 }
                else if (G < 0) { G = 0}

                //var B = (255 * (color.blue % 255).toDouble().pow(gamaDouble)).toInt()
                var B = (255 * (color.blue.toDouble() / 255).pow(gammaValue.toDouble())).toInt()
                if (B > 255) { B = 255 }
                else if (B < 0) { B = 0}

                bitmap.setPixel(x, y, Color.rgb(R, G, B))
            }
        }

        return bitmap
    }

}