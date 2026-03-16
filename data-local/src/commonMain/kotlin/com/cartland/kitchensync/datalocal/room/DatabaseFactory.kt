package com.cartland.kitchensync.datalocal.room

interface DatabaseFactory {
    fun create(): AppDatabase
}
