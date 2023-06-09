package com.htetarkarzaw.hanatest.domain.usecase

import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.domain.repository.HanaRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersUsecase @Inject constructor(private val repo: HanaRepositoryImpl) {
    suspend operator fun invoke(): Flow<Resource<String>> {
        return repo.fetchUsers()
    }
}