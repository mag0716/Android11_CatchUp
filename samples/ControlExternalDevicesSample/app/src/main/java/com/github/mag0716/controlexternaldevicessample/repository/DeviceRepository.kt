package com.github.mag0716.controlexternaldevicessample.repository

import com.github.mag0716.controlexternaldevicessample.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceRepository(private val deviceDao: DeviceDao) {

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
}