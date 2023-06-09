package com.htetarkarzaw.hanatest.domain.repository

import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.local.HanaDatabase
import com.htetarkarzaw.hanatest.data.remote.HanaApiService
import com.htetarkarzaw.hanatest.data.remote.RemoteResource
import com.htetarkarzaw.hanatest.data.remote.dto.UsersDTO
import com.htetarkarzaw.hanatest.data.remote.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HanaRepositoryImpl @Inject constructor(
    private val apiService: HanaApiService,
    db: HanaDatabase
) : HanaRepository {
    private val dao = db.userDao()
    override suspend fun fetchUsers(): Flow<Resource<UsersDTO>> = flow {
        when (val response = safeApiCall { apiService.fetchPopularMovies() }) {
            is RemoteResource.ErrorEvent -> {
                emit(Resource.Error(response.getErrorMessage()))
            }

            is RemoteResource.LoadingEvent -> {
                emit(Resource.Loading())
            }

            is RemoteResource.SuccessEvent -> {
                emit(Resource.Success(response.data!!))
            }
        }
    }
}