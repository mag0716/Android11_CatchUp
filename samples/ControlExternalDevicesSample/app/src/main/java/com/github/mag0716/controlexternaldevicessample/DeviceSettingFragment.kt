package com.github.mag0716.controlexternaldevicessample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

class DeviceSettingFragment : Fragment() {

    private lateinit var deviceNameInput: TextInputLayout
    private lateinit var deviceLocationInput: TextInputLayout
    private lateinit var settingButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceNameInput = view.findViewById(R.id.deviceNameInputLayout)
        deviceLocationInput = view.findViewById(R.id.deviceLocationInputLayout)
        settingButton = view.findViewById(R.id.settingButton)
        settingButton.setOnClickListener {
            deviceNameInput.error = null
            deviceLocationInput.error = null

            val deviceName = deviceNameInput.editText?.text.toString()
            val deviceLocation = deviceLocationInput.editText?.text.toString()
            if (deviceName.isBlank() || deviceLocation.isBlank()) {
                if (deviceName.isBlank()) {
                    deviceNameInput.error = "入力必須"
                }
                if (deviceLocation.isBlank()) {
                    deviceLocationInput.error = "入力必須"
                }
            } else {
                settingDevice(deviceName, deviceLocation)
            }
        }
    }

    private fun settingDevice(name: String, location: String) {
        // TODO: DB に保存
    }
}