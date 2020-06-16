package com.github.mag0716.controlexternaldevicessample.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.mag0716.controlexternaldevicessample.model.Device

@Database(entities = [Device::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}