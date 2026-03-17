@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.chriscartland.kitchensync.datalocal.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IosDatabaseFactory(
    private val databaseName: String = PRODUCTION_DATABASE_NAME,
) : DatabaseFactory {
    override fun create(): AppDatabase {
        val documentDirectory =
            NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
        val dbFilePath = requireNotNull(documentDirectory?.path) + "/$databaseName"
        return Room
            .databaseBuilder<AppDatabase>(
                name = dbFilePath,
            ).setDriver(BundledSQLiteDriver())
            .build()
    }
}
