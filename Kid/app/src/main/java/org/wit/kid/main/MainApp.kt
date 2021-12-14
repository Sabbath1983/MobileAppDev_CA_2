package org.wit.kid.main

import android.app.Application
import org.wit.kid.models.KidJSONStore
import org.wit.kid.models.KidMemStore
import org.wit.kid.models.KidStore
//import org.wit.kid.models.KidModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var kids: KidStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        kids = KidJSONStore(applicationContext)
        i("Kid started")
    }
}