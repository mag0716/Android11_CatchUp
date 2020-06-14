package com.github.mag0716.controlexternaldevicessample.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mag0716.controlexternaldevicessample.App
import com.github.mag0716.controlexternaldevicessample.model.Device
import kotlinx.coroutines.launch

class DeviceSettingViewModel(application: Application) : AndroidViewModel(application) {

    private val _addResult: MutableLiveData<Boolean> = MutableLiveData()
    val addResult: LiveData<Boolean> = _addResult

    private val _device: MutableLiveData<Device> = MutableLiveData()
    val device: LiveData<Device> = _device

    fun addOrUpdateDevice(name: String, location: String) {
        val deviceRepository = getApplication<App>().deviceRepository
        viewModelScope.launch {
            val device = _device.value
            if (device == null) {
                deviceRepository.addDevice(Device.createNewDevice(name, location))
            } else {
                deviceRepository.updateDevice(device.copy(name = name, placeLocation = location))
            }
            _addResult.value = true
        }
    }

    fun loadDevice(id: Int) {
        val deviceRepository = getApplication<App>().deviceRepository
        viewModelScope.launch {
            val device = deviceRepository.loadDevice(id)
            if (device != null) {
                _device.value = device
            }
        }
    }
}