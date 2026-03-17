package com.chriscartland.kitchensync.datalocal.room

interface DatabaseFactory {
    fun create(): AppDatabase
}
