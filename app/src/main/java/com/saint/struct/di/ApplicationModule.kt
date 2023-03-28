package com.saint.struct.di

import android.app.Application
import com.saint.struct.StructApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
//使用SingletonComponent代替ApplicationComponent
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideMyApplication(application: Application): StructApp {
        return application as StructApp
    }

}
