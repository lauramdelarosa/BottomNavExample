package com.delarosa.detailsurprise.app

import android.app.Application
import com.delarosa.detailsurprise.di.initDI

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
