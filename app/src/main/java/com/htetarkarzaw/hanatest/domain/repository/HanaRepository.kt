package com.htetarkarzaw.hanatest.domain.repository

import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.remote.dto.UsersDTO
import kotlinx.coroutines.flow.Flow

interface HanaRepository {
    suspend fun fetchUsers(): Flow<Resource<UsersDTO>>
}