package com.htetarkarzaw.hanatest.data.remote

import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import com.htetarkarzaw.hanatest.data.remote.dto.PostDTO
import com.htetarkarzaw.hanatest.data.remote.dto.UsersDTO
import com.htetarkarzaw.hanatest.utils.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface HanaApiService {
    @GET(Endpoint.HANA_GET_USERS)
    suspend fun fetchUsers(): Response<UsersDTO>

    @POST(Endpoint.HANA_POST)
    suspend fun uploadUser(
        @Body criteria: PostModelCriteria
    ): Response<PostDTO>
}