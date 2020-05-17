package com.github.mag0716.onetimepermissionsample

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ExplainPermissionRequirementDialog.OnClickListener {

    companion object {
        private const val TAG = "OneTimePermission"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // one-time permission が選択できる
        button1.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            ) {
                ExplainPermissionRequirementDialog.newInstance(Manifest.permission.ACCESS_FINE_LOCATION)
                    .show(
                        supportFragmentManager,
                        ExplainPermissionRequirementDialog::class.java.simpleName
                    )
                return@setOnClickListener
            }
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        button2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        ExplainPermissionRequirementDialog.newInstance(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            .show(
                                supportFragmentManager,
                                ExplainPermissionRequirementDialog::class.java.simpleName
                            )
                        return@setOnClickListener
                    }
                    ExplainPermissionRequirementDialog.newInstance(Manifest.permission.ACCESS_FINE_LOCATION)
                        .show(
                            supportFragmentManager,
                            ExplainPermissionRequirementDialog::class.java.simpleName
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
            requestPermission(Manifest.permission.RECORD_AUDIO)
        }
        button4.setOnClickListener {
            requestPermission(Manifest.permission.CAMERA)
        }

        // one-time permission が選択できない
        button5.setOnClickListener {
            requestPermission(Manifest.permission.READ_CALENDAR)
        }
        button6.setOnClickListener {
            requestPermission(Manifest.permission.READ_CONTACTS)
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

    override fun requestPermission(permission: String) {
        if (isGranted(permission)) {
            Toast.makeText(
                this,
                "$permission is granted.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                0
            )
        }
    }

    override fun goToSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        }
        startActivity(intent)
    }

    private fun isGranted(feature: String) =
        ContextCompat.checkSelfPermission(this, feature) == PackageManager.PERMISSION_GRANTED
}

class ExplainPermissionRequirementDialog : DialogFragment() {

    interface OnClickListener {
        fun requestPermission(permission: String)
        fun goToSettings()
    }

    companion object {
        private const val KEY_PERMISSION = "Permission"
        fun newInstance(permission: String): ExplainPermissionRequirementDialog {
            return ExplainPermissionRequirementDialog().apply {
                arguments = bundleOf(
                    KEY_PERMISSION to permission
                )
            }
        }
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
        val permission = arguments?.getString(KEY_PERMISSION)
            ?: throw IllegalArgumentException("must set argument.")
        val message =
            when (permission) {
                Manifest.permission.ACCESS_BACKGROUND_LOCATION ->
                    """
                    機能を利用するためアプリ利用中以外でも位置情報の取得を行う必要があります。
                    設定画面で ${packageManager.backgroundPermissionOptionLabel} を選択して、位置情報の取得を許可してください。
                    """.trimIndent()
                else ->
                    """
                    機能を利用するためには権限を許可いただく必要があります。
                    設定画面より権限の許可をおこなってください。
                    """.trimIndent()
            }
        builder.setMessage(message)
            .setNegativeButton("キャンセル", null)
            .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                when (permission) {
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION -> listener.requestPermission(
                        permission
                    )
                    else -> listener.goToSettings()
                }
            }
        return builder.create()
    }
}