package com.htetarkarzaw.hanatest.di

import android.content.Context
import androidx.room.Room
import com.htetarkarzaw.hanatest.data.local.HanaDatabase
import com.htetarkarzaw.hanatest.data.local.dao.UserDao
import com.htetarkarzaw.hanatest.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun providesUserDao(
        db: HanaDatabase
    ): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context):HanaDatabase{
        return Room.databaseBuilder(context,HanaDatabase::class.java, Constant.HANA_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }
}
