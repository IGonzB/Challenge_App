package com.hsbc.challenge.di


import android.app.Application
import android.content.Context
import com.hsbc.challenge.model.WeatherRepository
import com.hsbc.challenge.model.WeatherRepositoryLocal
import com.hsbc.challenge.util.common.ErrorTypeToErrorTextConverter
import com.hsbc.challenge.util.common.ErrorTypeToErrorTextConverterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideUserRepository(): WeatherRepository {
        return WeatherRepositoryLocal(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideErrorTypeToErrorTextConverter(): ErrorTypeToErrorTextConverter {
        return ErrorTypeToErrorTextConverterImpl()
    }
}