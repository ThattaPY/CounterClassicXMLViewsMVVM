package com.thatta.counterclassicxmlviewsmvvm.di

import com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable.FlagsDao
import com.thatta.counterclassicxmlviewsmvvm.data.repositories.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideDataRepository(flagsDao: FlagsDao): DataRepository {
        return DataRepository(flagsDao)
    }
}