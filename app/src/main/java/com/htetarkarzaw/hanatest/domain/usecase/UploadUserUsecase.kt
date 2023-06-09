package com.htetarkarzaw.hanatest.domain.usecase

import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import com.htetarkarzaw.hanatest.domain.repository.HanaRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadUserUsecase @Inject constructor(private val repo: HanaRepositoryImpl) {
    suspend operator fun invoke(criteria: PostModelCriteria): Flow<Resource<String>> {
        return repo.uploadUser(criteria)
    }
}