package com.htetarkarzaw.hanatest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htetarkarzaw.hanatest.data.local.dao.UserDao
import com.htetarkarzaw.hanatest.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class HanaDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}