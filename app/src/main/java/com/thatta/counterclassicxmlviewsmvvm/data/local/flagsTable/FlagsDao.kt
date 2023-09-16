package com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thatta.counterclassicxmlviewsmvvm.data.dataUtils.DataConstants
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

@Dao
interface FlagsDao {
    @Query("SELECT * FROM ${DataConstants.TABLE_NAME}")
    fun getAllFlags(): LiveData<List<FlagsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlag(flag: FlagsModel)

    @Query("DELETE FROM ${DataConstants.TABLE_NAME}")
    fun deleteAllFlags()
}