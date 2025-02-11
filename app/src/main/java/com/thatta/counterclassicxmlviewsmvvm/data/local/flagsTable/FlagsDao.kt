package com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thatta.counterclassicxmlviewsmvvm.data.dataUtils.DataConstants
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FlagsDao {
    // Get all flags from the table, used Flow to be able to observe changes in the table
    @Query("SELECT * FROM ${DataConstants.TABLE_NAME}")
    fun getAllFlags(): Flow<List<FlagsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlag(flag: FlagsModel)

    @Query("DELETE FROM ${DataConstants.TABLE_NAME}")
    fun deleteAllFlags()
}