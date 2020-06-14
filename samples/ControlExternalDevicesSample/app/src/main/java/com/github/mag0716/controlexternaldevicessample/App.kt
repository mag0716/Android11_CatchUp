package com.github.mag0716.controlexternaldevicessample

import android.app.Application
import androidx.room.Room
import com.github.mag0716.controlexternaldevicessample.repository.AppDatabase
import com.github.mag0716.controlexternaldevicessample.repository.DeviceRepository

class App : Application() {

    private lateinit var db: AppDatabase

    val deviceRepository: DeviceRepository by lazy {
        DeviceRepository(db.deviceDao())
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "database"
        ).build()
    }
}