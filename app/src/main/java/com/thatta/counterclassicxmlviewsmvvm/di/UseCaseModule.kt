package com.thatta.counterclassicxmlviewsmvvm.di

import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.CounterUseCaseInterface
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.GetAllFlagsUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.GetAllFlagsUseCaseInterface
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCase
import com.thatta.counterclassicxmlviewsmvvm.domain.usesCases.InsertFlagUseCaseInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindCounterUseCase(impl: CounterUseCase): CounterUseCaseInterface

    @Binds
    abstract fun bindInsertFlagUseCase(impl: InsertFlagUseCase): InsertFlagUseCaseInterface

    @Binds
    abstract fun bindGetAllFlagsUseCase(impl: GetAllFlagsUseCase): GetAllFlagsUseCaseInterface

}