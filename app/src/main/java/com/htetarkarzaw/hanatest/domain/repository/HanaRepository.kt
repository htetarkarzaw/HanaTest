package com.htetarkarzaw.hanatest.domain.repository

import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.local.entity.User
import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import kotlinx.coroutines.flow.Flow
import java.io.File

interface HanaRepository {
    suspend fun fetchUsers(): Flow<Resource<String>>
    suspend fun uploadUser(userCriteria: PostModelCriteria): Flow<Resource<String>>

    suspend fun createJsonFile(file: File): Flow<Resource<String>>
    suspend fun insertUsers(users: List<User>)
    suspend fun retrieveUsers(): Flow<List<User>>
    suspend fun retrieveUserByUserId(userId: Int): Flow<User>
}