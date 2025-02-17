package com.thatta.counterclassicxmlviewsmvvm.data.repositories

import com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable.FlagsDao
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val flagsDao: FlagsDao
) {

    fun getAllFlags() = flagsDao.getAllFlags()

    suspend fun insertFlag(flag: FlagsModel) = flagsDao.insertFlag(flag)

    fun deleteAllFlags() = flagsDao.deleteAllFlags()
}