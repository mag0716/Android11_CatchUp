package com.github.mag0716.packagevisibilitysample

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PackageVisibility"
        private const val TEST_URL =
            "https://developer.android.com/about/versions/11/privacy/package-visibility"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            getInstalledApplications()
        }
        button2.setOnClickListener {
            val intent = Intent().apply {
                setClassName(
                    "com.github.mag0716.packagevisibility.target",
                    "com.github.mag0716.packagevisibility.target.MainActivity"
                )
            }
            startActivity(intent)
        }
        button3.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, TEST_URL.toUri())
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Browser App does not exists.", Toast.LENGTH_SHORT).show()
            }
        }
        button4.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, TEST_URL.toUri())
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Browser App does not exists.", Toast.LENGTH_SHORT).show()
            }
        }
        button5.setOnClickListener {

        }
    }

    fun getInstalledApplications() {
        val flags = PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES
        val installedApplications = packageManager.getInstalledApplications(flags)
        val installedPackages = packageManager.getInstalledPackages(flags)

        Log.d(TAG, "installedApplications: ")
        for (installedApplication in installedApplications) {
            Log.d(TAG, "$installedApplication")
        }
        Log.d(TAG, "")
        Log.d(TAG, "installedPackages: ")
        for (installedPackage in installedPackages) {
            Log.d(TAG, "$installedPackage")
        }
    }
}
