package com.github.mag0716.controlexternaldevicessample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class Device(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val placeLocation: String
)