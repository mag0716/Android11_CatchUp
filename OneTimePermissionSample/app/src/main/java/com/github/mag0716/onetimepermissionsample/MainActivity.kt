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
            val features = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            requestPermission(features)

        }
        button2.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.RECORD_AUDIO))
        }
        button3.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.CAMERA))
        }

        // one-time permission が選択できない
        button4.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.READ_CALENDAR))
        }
        button5.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.READ_CONTACTS))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionResult : ${permissions[0]}, ${grantResults[0]}")
    }

    private fun requestPermission(features: Array<String>) {
        val feature = features[0]
        if (ContextCompat.checkSelfPermission(this, feature)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val t = arrayOf(features)
            Toast.makeText(
                this,
                "$feature's permission is granted.",
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
}
