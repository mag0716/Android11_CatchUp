package com.github.mag0716.onetimepermissionsample

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        button2.setOnClickListener {
            requestPermission(Manifest.permission.RECORD_AUDIO)
        }
        button3.setOnClickListener {
            requestPermission(Manifest.permission.CAMERA)
        }

        // one-time permission が選択できない
        button4.setOnClickListener {
            requestPermission(Manifest.permission.READ_CALENDAR)
        }
        button5.setOnClickListener {
            requestPermission(Manifest.permission.READ_CONTACTS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionResult : $permissions, $grantResults")
    }

    private fun requestPermission(feature: String) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(feature),
            0
        )
    }
}
