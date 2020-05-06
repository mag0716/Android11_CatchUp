package com.github.mag0716.shareddatasetssample.accessableapp

import android.app.blob.BlobHandle
import android.app.blob.BlobStoreManager
import android.content.Context
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Shared Datasets Sample"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accessButton.setOnClickListener {
            accessSharedData()
        }
    }

    private fun accessSharedData() {
        val blobStoreManager = getSystemService(Context.BLOB_STORE_SERVICE) as BlobStoreManager
        val digest = MessageDigest.getInstance("SHA-256")
        val expiryTimeMills = SimpleDateFormat("yyyyMMddHHmmss").parse("20201231235959").time
        val blobHandle = BlobHandle.createWithSha256(
            digest.digest("Shared Datasets Sample".toByteArray(StandardCharsets.UTF_8)),
            "test.txt",
            expiryTimeMills,
            "test.txt tag"
        )

        var pfd: ParcelFileDescriptor.AutoCloseInputStream? = null
        try {
            pfd = ParcelFileDescriptor.AutoCloseInputStream(blobStoreManager.openBlob(blobHandle))
            val bytes = pfd.readBytes()
            val data = bytes.toString(StandardCharsets.UTF_8)
            Toast.makeText(
                this,
                "shared dataset : $data",
                Toast.LENGTH_SHORT
            )
                .show()
        } catch (e: Exception) {
            Log.e(TAG, "failed accessSharedData", e)
        } finally {
            pfd?.close()
        }
    }
}
