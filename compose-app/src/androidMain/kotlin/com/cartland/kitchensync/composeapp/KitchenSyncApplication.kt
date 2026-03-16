package com.cartland.kitchensync.composeapp

import android.app.Application
import com.cartland.kitchensync.composeapp.di.AppComponent
import com.cartland.kitchensync.composeapp.di.create

class KitchenSyncApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent::class.create()
    }
}
