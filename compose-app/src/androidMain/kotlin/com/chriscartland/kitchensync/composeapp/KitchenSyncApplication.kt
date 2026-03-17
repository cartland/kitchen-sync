package com.chriscartland.kitchensync.composeapp

import android.app.Application
import com.chriscartland.kitchensync.composeapp.di.AppComponent
import com.chriscartland.kitchensync.composeapp.di.create

class KitchenSyncApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent::class.create()
    }
}
