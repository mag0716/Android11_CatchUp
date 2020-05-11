package com.github.mag0716.onetimepermissionsample

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ExplainBackgroundLocationRequirementDialog.OnClickListener {

    companion object {
        private const val TAG = "OneTimePermission"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // one-time permission が選択できる
        button1.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
        button2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    val dialogFragment = ExplainBackgroundLocationRequirementDialog()
                    dialogFragment.show(
                        supportFragmentManager,
                        ExplainBackgroundLocationRequirementDialog::class.java.simpleName
                    )
                } else {
                    Toast.makeText(
                        this,
                        "must grant ACCESS_FINE_LOCATION",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    override fun onOkClicked() {
        requestPermission(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
    }
}

@TargetApi(30)
class ExplainBackgroundLocationRequirementDialog : DialogFragment() {

    interface OnClickListener {
        fun onOkClicked()
    }

    lateinit var listener: OnClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        val packageManager = activity.application.packageManager
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(
            """
            機能を利用するためアプリ利用中以外でも位置情報の取得を行う必要があります。
            設定画面で ${packageManager.backgroundPermissionOptionLabel} を選択して、位置情報の取得を許可してください。
            """.trimIndent()
        )
            .setNegativeButton("キャンセル", null)
            .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                listener.onOkClicked()
            }
        return builder.create()
    }
}