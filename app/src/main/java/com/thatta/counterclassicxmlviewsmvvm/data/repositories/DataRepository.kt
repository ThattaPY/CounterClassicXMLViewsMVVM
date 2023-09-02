package com.thatta.counterclassicxmlviewsmvvm.data.repositories

import com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable.FlagsDao
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

class DataRepository(
    private val flagsDao: FlagsDao
) {
    suspend fun getAllFlags() = flagsDao.getAllFlags()
    suspend fun insertFlag(flag: FlagsModel) = flagsDao.insertFlag(flag)
    suspend fun deleteAllFlags() = flagsDao.deleteAllFlags()
}