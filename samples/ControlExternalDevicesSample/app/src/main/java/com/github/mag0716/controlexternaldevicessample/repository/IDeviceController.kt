package com.github.mag0716.controlexternaldevicessample.repository

interface IDeviceController {

    suspend fun isConnected(id: Int): Boolean

    suspend fun getStatus(id: Int): Boolean

    suspend fun updateStatus(id: Int, isOn: Boolean)
}