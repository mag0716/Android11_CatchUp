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

    fun addDevice(name: String, location: String) {
        val deviceRepository = getApplication<App>().deviceRepository
        viewModelScope.launch {
            deviceRepository.addDevice(Device.createNewDevice(name, location))
            _addResult.value = true
        }
    }
}