package com.chriscartland.kitchensync.datalocal.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "placeholder")
data class PlaceholderEntity(
    @PrimaryKey
    val id: Long = 0,
)
