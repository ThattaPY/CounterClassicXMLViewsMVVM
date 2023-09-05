package com.thatta.counterclassicxmlviewsmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thatta.counterclassicxmlviewsmvvm.data.dataUtils.DataConstants
import com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable.FlagsDao
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [FlagsModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun flagsDao(): FlagsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DataConstants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}