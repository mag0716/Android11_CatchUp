package com.github.mag0716.controlexternaldevicessample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mag0716.controlexternaldevicessample.model.Device
import kotlinx.coroutines.launch

class DeviceListViewModel(application: Application) : AndroidViewModel(application) {

    private val _deviceList: MutableLiveData<List<Device>> = MutableLiveData()
    val deviceList: LiveData<List<Device>> = _deviceList

    fun loadDeviceList() {
        val deviceRepository = getApplication<App>().deviceRepository
        viewModelScope.launch {
            _deviceList.value = deviceRepository.loadDevices()
        }
    }
}