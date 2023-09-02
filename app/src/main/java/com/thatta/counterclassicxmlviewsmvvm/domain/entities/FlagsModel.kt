package com.thatta.counterclassicxmlviewsmvvm.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thatta.counterclassicxmlviewsmvvm.data.dataUtils.DataConstants

@Entity(tableName = DataConstants.TABLE_NAME)
data class FlagsModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DataConstants.COLUMN_NAME_ID) val id: Int? = null,
    @ColumnInfo(name = DataConstants.COLUMN_NAME_FLAG) val flag: String
)
