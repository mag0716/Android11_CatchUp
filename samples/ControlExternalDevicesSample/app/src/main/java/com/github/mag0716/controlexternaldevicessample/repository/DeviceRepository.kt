package com.github.mag0716.controlexternaldevicessample.repository

import com.github.mag0716.controlexternaldevicessample.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceRepository(
    private val deviceDao: DeviceDao,
    private val deviceController: IDeviceController
) {

    suspend fun loadDevices(): List<Device> = withContext(Dispatchers.IO) {
        deviceDao.loadDevices()
    }

    suspend fun loadDevice(id: Int): Device? = withContext(Dispatchers.IO) {
        deviceDao.loadDeviceById(id)
    }

    suspend fun addDevice(device: Device) = withContext(Dispatchers.IO) {
        deviceDao.insertDevices(device)
    }

    suspend fun updateDevice(device: Device) = withContext(Dispatchers.IO) {
        deviceDao.updateDevices(device)
    }

    suspend fun deleteDevice(id: Int) = withContext(Dispatchers.IO) {
        val device = loadDevice(id)
        if (device != null) {
            deviceDao.delete(device)
        }
    }

    suspend fun isConnected(id: Int): Boolean = withContext(Dispatchers.IO) {
        deviceController.isConnected(id)
    }

    suspend fun getStatus(id: Int): Boolean = withContext(Dispatchers.IO) {
        deviceController.getStatus(id)
    }

    suspend fun updateStatus(id: Int, isOn: Boolean) = withContext(Dispatchers.IO) {
        deviceController.updateStatus(id, isOn)
    }
}