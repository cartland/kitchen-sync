package com.cartland.kitchensync.datalocal.room

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

class AndroidDatabaseFactory(
    private val context: Context,
    private val databaseName: String = PRODUCTION_DATABASE_NAME,
) : DatabaseFactory {
    override fun create(): AppDatabase {
        val dbFile = context.getDatabasePath(databaseName)
        return Room
            .databaseBuilder<AppDatabase>(
                context = context.applicationContext,
                name = dbFile.absolutePath,
            ).setDriver(BundledSQLiteDriver())
            .build()
    }
}
