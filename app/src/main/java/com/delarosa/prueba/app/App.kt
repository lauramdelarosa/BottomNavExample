package com.delarosa.prueba.app

import android.app.Application
import com.delarosa.prueba.di.initDI

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}