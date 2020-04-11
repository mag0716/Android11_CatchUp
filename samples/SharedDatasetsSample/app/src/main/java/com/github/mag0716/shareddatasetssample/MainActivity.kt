package com.github.mag0716.shareddatasetssample

import android.app.blob.BlobHandle
import android.app.blob.BlobStoreManager
import android.content.Context
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SharedDatasets"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            shareDataToPublic()
        }
    }

    private fun shareDataToPublic() {
        val data = assets.open("test.txt")
                .reader(charset = Charsets.UTF_8)
                .use { it.readText() }

        val digest = MessageDigest.getInstance("SHA-256")

        val blobStoreManager = getSystemService(Context.BLOB_STORE_SERVICE) as BlobStoreManager
        val blobHandle = BlobHandle.createWithSha256(
                digest.digest(data.toByteArray(StandardCharsets.UTF_8)),
                "test.txt",
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1),
                "test.txt tag"
        )
        val sessionId = blobStoreManager.createSession(blobHandle)
        try {
            val session = blobStoreManager.openSession(sessionId)
            val text = ParcelFileDescriptor.AutoCloseOutputStream(
                    session.openWrite(0, data.length.toLong())
            )
            text.write(data.toByteArray(StandardCharsets.UTF_8))
            session.apply {
                allowPublicAccess()
                commit(mainExecutor, Consumer<Int> {
                    Log.d(TAG, "callback : $it")
                })
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed sharedDataToPublic", e)
        }
    }
}
