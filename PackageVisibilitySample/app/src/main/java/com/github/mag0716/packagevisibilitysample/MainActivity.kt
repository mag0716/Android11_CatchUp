package com.github.mag0716.packagevisibilitysample

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

        button.setOnClickListener {
            getInstalledApplications()
        }
    }

    fun getInstalledApplications() {
        val installedApplications = packageManager.getInstalledApplications(0)
        val installedPackages = packageManager.getInstalledPackages(0)

        Log.d(TAG, "installedApplications: $installedApplications")
        Log.d(TAG, "installedPackages: $installedPackages")
    }
}
