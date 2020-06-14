package com.github.mag0716.controlexternaldevicessample.view

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mag0716.controlexternaldevicessample.R
import com.google.android.material.textfield.TextInputLayout

class DeviceSettingFragment : Fragment() {

    private val args: DeviceSettingFragmentArgs by navArgs()
    private val viewModel by viewModels<DeviceSettingViewModel>()

    private val isUpdate: Boolean by lazy {
        args.deviceId != 0
    }

    private lateinit var deviceNameInput: TextInputLayout
    private lateinit var deviceLocationInput: TextInputLayout
    private lateinit var settingButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(isUpdate)
    }

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

        viewModel.databaseOperationResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
            }
        })
        viewModel.device.observe(viewLifecycleOwner, Observer {
            deviceNameInput.editText?.setText(it.name)
            deviceLocationInput.editText?.setText(it.placeLocation)
        })
    }

    override fun onResume() {
        super.onResume()
        if (isUpdate) {
            viewModel.loadDevice(args.deviceId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (isUpdate) {
            inflater.inflate(R.menu.menu_device_setting, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delete -> {
                viewModel.deleteDevice(args.deviceId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun settingDevice(name: String, location: String) {
        viewModel.addOrUpdateDevice(name, location)
    }
}