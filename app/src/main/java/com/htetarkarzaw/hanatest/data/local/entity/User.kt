package com.htetarkarzaw.hanatest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.htetarkarzaw.hanatest.utils.Constant

@Entity(tableName = Constant.HANA_TABLE_NAME)
data class User (
    @PrimaryKey(autoGenerate = false)
    val id: String,
)