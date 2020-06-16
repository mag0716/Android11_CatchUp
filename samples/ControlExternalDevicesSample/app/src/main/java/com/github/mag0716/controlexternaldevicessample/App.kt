package com.github.mag0716.controlexternaldevicessample

import android.app.Application
import androidx.room.Room
import com.github.mag0716.controlexternaldevicessample.repository.AppDatabase
import com.github.mag0716.controlexternaldevicessample.repository.DeviceRepository
import com.github.mag0716.controlexternaldevicessample.repository.IDeviceController
import kotlinx.coroutines.delay

class App : Application() {

    private lateinit var db: AppDatabase

    val deviceRepository: DeviceRepository by lazy {
        DeviceRepository(db.deviceDao(), FakeDeviceController())
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    private class FakeDeviceController : IDeviceController {

        private val statusMap = mutableMapOf<Int, Boolean>()

        override suspend fun isConnected(id: Int): Boolean {
            delay((0..1000).random().toLong())
            return true
        }

        override suspend fun getStatus(id: Int): Boolean {
            delay((0..1000).random().toLong())
            return statusMap[id] ?: false
        }

        override suspend fun updateStatus(id: Int, isOn: Boolean) {
            delay((0..1000).random().toLong())
            statusMap[id] = isOn
        }

    }
}