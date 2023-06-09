package com.htetarkarzaw.hanatest.domain.usecase

import com.htetarkarzaw.hanatest.domain.repository.HanaRepositoryImpl
import com.htetarkarzaw.hanatest.domain.vo.UserVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RetrieveUserByIdUsecase @Inject constructor(private val repo: HanaRepositoryImpl) {
    suspend operator fun invoke(userId:Int): Flow<UserVO> {
        return repo.retrieveUserByUserId(userId).map { it.toVO() }
    }
}