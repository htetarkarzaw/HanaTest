package com.htetarkarzaw.hanatest.domain.repository

import android.util.Log
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.local.dao.UserDao
import com.htetarkarzaw.hanatest.data.local.entity.User
import com.htetarkarzaw.hanatest.data.remote.HanaApiService
import com.htetarkarzaw.hanatest.data.remote.RemoteResource
import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import com.htetarkarzaw.hanatest.data.remote.dto.UserDTO
import com.htetarkarzaw.hanatest.data.remote.handleException
import com.htetarkarzaw.hanatest.data.remote.safeApiCall
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

class HanaRepositoryImpl @Inject constructor(
    private val apiService: HanaApiService,
    private val dao: UserDao
) : HanaRepository {
    override suspend fun fetchUsers(): Flow<Resource<String>> = flow {
        when (val response = safeApiCall { apiService.fetchUsers() }) {
            is RemoteResource.ErrorEvent -> {
                emit(Resource.Error(response.getErrorMessage()))
            }

            is RemoteResource.LoadingEvent -> {
                emit(Resource.Loading())
            }

            is RemoteResource.SuccessEvent -> {
                response.data?.let {
                    insertUsers(users = it.map { dto -> dto.toEntity() })
                }
                emit(Resource.Success("Success"))
            }

        }
    }

    override suspend fun uploadUser(userCriteria: PostModelCriteria): Flow<Resource<String>> = flow {
        when (val response = safeApiCall { apiService.uploadUser(criteria = userCriteria) }) {
            is RemoteResource.ErrorEvent -> {
                emit(Resource.Error(response.getErrorMessage()))
            }

            is RemoteResource.LoadingEvent -> {
                emit(Resource.Loading())
            }

            is RemoteResource.SuccessEvent -> {
                val result = response.data?.id
                emit(Resource.Success("Uploaded Success: UserId is $result"))
            }
        }
    }

    override suspend fun createJsonFile(file: File): Flow<Resource<String>> = flow {
        val users = retrieveUsers().first().map { it.toDto() }
        if (users.isNotEmpty()) {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val usersType = Types.newParameterizedType(List::class.java, UserDTO::class.java)
            val jsonAdapter: JsonAdapter<List<UserDTO>> = moshi.adapter(usersType)
            val json = jsonAdapter.toJson(users)
            try {
                withContext(Dispatchers.IO) {
                    FileWriter(file).use { writer ->
                        writer.write(json)
                    }
                }
                Log.e("hakz.repo.createfile", "User data saved to ${file.absolutePath}")
                emit(Resource.Success("User data saved to ${file.absolutePath}"))
            } catch (e: Exception) {
                val error = handleException(e)
                emit(Resource.Error(error.message))
            }
        } else {
            emit(Resource.Error("There is no user data."))
        }
    }

    override suspend fun insertUsers(users: List<User>) {
        dao.insertUsers(users)
    }

    override suspend fun retrieveUsers(): Flow<List<User>> {
        return dao.retrieveUsers()

    }

    override suspend fun retrieveUserByUserId(userId: Int): Flow<User> {
        return dao.retrieveUserByIdViaFlow(userId)

    }
}