package com.github.mag0716.controlexternaldevicessample.repository

import androidx.room.*
import com.github.mag0716.controlexternaldevicessample.model.Device

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices")
    suspend fun loadDevices(): List<Device>

    @Query("SELECT * FROM devices WHERE id IN (:id)")
    suspend fun loadDeviceById(id: Int): Device

    @Insert
    suspend fun insertDevices(vararg devices: Device)

    @Update
    suspend fun updateDevices(vararg devices: Device)

    @Delete
    suspend fun delete(device: Device)
}