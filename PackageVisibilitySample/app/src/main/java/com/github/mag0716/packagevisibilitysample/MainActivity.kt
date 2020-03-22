package com.github.mag0716.packagevisibilitysample

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PackageVisibility"
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
