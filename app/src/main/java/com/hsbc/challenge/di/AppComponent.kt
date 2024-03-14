package com.hsbc.challenge.di;

import com.hsbc.challenge.viewmodel.MainViewModel
import com.hsbc.challenge.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun mainViewModel(): MainViewModel
    fun viewModelFactory(): ViewModelFactory
}