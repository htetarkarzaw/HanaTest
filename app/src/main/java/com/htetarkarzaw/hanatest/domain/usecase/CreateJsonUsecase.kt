package com.htetarkarzaw.hanatest.domain.usecase

import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.domain.repository.HanaRepositoryImpl
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class CreateJsonUsecase @Inject constructor(private val repo: HanaRepositoryImpl) {
    suspend operator fun invoke(file: File): Flow<Resource<String>> {
        return repo.createJsonFile(file)
    }
}