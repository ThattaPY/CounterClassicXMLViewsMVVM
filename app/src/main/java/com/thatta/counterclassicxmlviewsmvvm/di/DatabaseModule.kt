package com.thatta.counterclassicxmlviewsmvvm.di

import android.app.Application
import androidx.room.Room
import com.thatta.counterclassicxmlviewsmvvm.data.dataUtils.DataConstants
import com.thatta.counterclassicxmlviewsmvvm.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DataConstants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFlagsDao(appDatabase: AppDatabase) = appDatabase.flagsDao()

}