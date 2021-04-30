package com.example.registeruser

import android.app.Application
import com.example.registeruser.di.apiModule
import com.example.registeruser.di.networkModule
import com.example.registeruser.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Initializing Database
         **/
        AppDatabase.init(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(networkModule, apiModule)
        }
    }
}