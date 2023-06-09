package com.htetarkarzaw.hanatest.data.local.dao

import androidx.room.*
import com.htetarkarzaw.hanatest.data.local.entity.User
import com.htetarkarzaw.hanatest.utils.Constant
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<User>)

    @Query("SELECT * FROM ${Constant.HANA_TABLE_NAME}")
    fun retrievesUpcomingMovies(): Flow<List<User>>

    @Query("SELECT * FROM ${Constant.HANA_TABLE_NAME} WHERE id == :id")
    suspend fun getMovieById(id: Long): User?

    @Query("SELECT * FROM ${Constant.HANA_TABLE_NAME} WHERE id == :id")
    fun getMovieByIdViaFlow(id: Long): Flow<User>

    @Update
    suspend fun updateMovie(movie: User): Int
}