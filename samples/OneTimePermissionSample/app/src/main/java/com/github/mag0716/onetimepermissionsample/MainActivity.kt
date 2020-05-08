package com.github.mag0716.onetimepermissionsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OneTimePermission"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // one-time permission が選択できる
        button1.setOnClickListener {
//            val features = when {
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
//                    // TODO: Android 11 preview1 - 3 ではクラッシュする
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                    )
//                }
//                else -> {
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    )
//                }
//            }
//            requestPermission(features)
            requestPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
        button2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermission(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            } else {
                Toast.makeText(
                    this,
                    "ACCESS_BACKGROUND_LOCATION is not support.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        button3.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.RECORD_AUDIO))
        }
        button4.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.CAMERA))
        }

        // one-time permission が選択できない
        button5.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.READ_CALENDAR))
        }
        button6.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.READ_CONTACTS))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for ((index, permission) in permissions.withIndex()) {
            Log.d(TAG, "onRequestPermissionResult : $permission, ${grantResults[index]}")
        }
    }

    private fun requestPermission(features: Array<String>) {
        if (features.asSequence().all { isGranted(it) }) {
            Toast.makeText(
                this,
                "${features.toList()} is granted.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                features,
                0
            )
        }
    }

    private fun isGranted(feature: String) =
        ContextCompat.checkSelfPermission(this, feature) == PackageManager.PERMISSION_GRANTED
}
