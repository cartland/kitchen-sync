package com.cartland.kitchensync.datalocal.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

class JvmDatabaseFactory(
    private val databaseName: String = PRODUCTION_DATABASE_NAME,
) : DatabaseFactory {
    override fun create(): AppDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), databaseName)
        return Room
            .databaseBuilder<AppDatabase>(
                name = dbFile.absolutePath,
            ).setDriver(BundledSQLiteDriver())
            .build()
    }
}
