package com.prokkypew.infinitecavystory

import android.app.Application
import android.content.Context

/**
 * Created by prokk on 16.08.2017.
 */
class MainApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}