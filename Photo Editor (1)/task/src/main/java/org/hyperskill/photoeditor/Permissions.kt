package org.hyperskill.photoeditor

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val MEDIA_REQUEST_CODE = 0

class Permissions {

    fun hasWritePermission(context: Context) = ContextCompat.checkSelfPermission( context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


    fun requestWritePermission(context: Context, activity: Activity) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(context, "Storage permission is granted", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat
                .shouldShowRequestPermissionRationale(activity  ,Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {

                AlertDialog.Builder(context)
                    .setTitle("Permission required")
                    .setMessage("This app needs permission to access this feature.")
                    .setPositiveButton("Grant") { _, _ ->
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MEDIA_REQUEST_CODE,
                        )
                    }
                    .setNegativeButton("Cancel", null)
                    .show()

            }
            else -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MEDIA_REQUEST_CODE,
                )
            }
        }
    }
}