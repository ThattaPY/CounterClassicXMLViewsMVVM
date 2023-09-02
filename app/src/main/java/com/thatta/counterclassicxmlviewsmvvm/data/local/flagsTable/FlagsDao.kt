package com.thatta.counterclassicxmlviewsmvvm.data.local.flagsTable

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thatta.counterclassicxmlviewsmvvm.domain.entities.FlagsModel

@Dao
interface FlagsDao {
    @Query("SELECT * FROM flags_table")
    suspend fun getAllFlags(): LiveData<List<FlagsModel>>
    @Insert
    suspend fun insertFlag(flag: FlagsModel)

    @Query("DELETE FROM flags_table")
    suspend fun deleteAllFlags()
}