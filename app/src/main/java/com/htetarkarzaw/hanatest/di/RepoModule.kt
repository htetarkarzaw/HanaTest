package com.htetarkarzaw.hanatest.di

import com.htetarkarzaw.hanatest.data.local.dao.UserDao
import com.htetarkarzaw.hanatest.data.remote.HanaApiService
import com.htetarkarzaw.hanatest.domain.repository.HanaRepository
import com.htetarkarzaw.hanatest.domain.repository.HanaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {
    @Provides
    @Singleton
    fun provideHanaRepository(apiService: HanaApiService, dao: UserDao): HanaRepository {
        return HanaRepositoryImpl(apiService = apiService, dao = dao)
    }
}