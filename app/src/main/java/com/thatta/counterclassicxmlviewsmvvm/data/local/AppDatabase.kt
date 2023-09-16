package com.thatta.counterclassicxmlviewsmvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable.FlagsDao
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

@Database(
    entities = [FlagsModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun flagsDao(): FlagsDao

}