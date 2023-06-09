package com.htetarkarzaw.hanatest.data.remote

import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import com.htetarkarzaw.hanatest.data.remote.dto.PostDTO
import com.htetarkarzaw.hanatest.data.remote.dto.UsersDTO
import com.htetarkarzaw.hanatest.utils.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET


interface HanaApiService {

    @GET(Endpoint.HANA_GET_USERS)
    suspend fun fetchPopularMovies(): Response<UsersDTO>

    @GET(Endpoint.HANA_POST)
    suspend fun fetchUpcomingMovie(
        @Body criteria: PostModelCriteria
    ): Response<PostDTO>
}