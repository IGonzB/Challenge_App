package com.hsbc.challenge

import android.app.Application
import com.hsbc.challenge.di.AppComponent
import com.hsbc.challenge.di.AppModule
import com.hsbc.challenge.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = this.initDagger()
    }

    private fun initDagger() = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
}