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
    version = 5,
    exportSchema = false
//    ,autoMigrations = [AutoMigration (from = 1, to = 2)]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun flagsDao(): FlagsDao

    private class CounterDatabaseCallback(private val scope: CoroutineScope)
        : Callback()

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val  instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DataConstants.DATABASE_NAME)
                    .addCallback(CounterDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}