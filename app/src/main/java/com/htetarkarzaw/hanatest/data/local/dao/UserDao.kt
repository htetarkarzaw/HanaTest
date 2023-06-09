package com.htetarkarzaw.hanatest.data.local.dao

import androidx.room.*
import com.htetarkarzaw.hanatest.data.local.entity.User
import com.htetarkarzaw.hanatest.utils.Constant
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(movies: List<User>)

    @Query("SELECT * FROM ${Constant.HANA_TABLE_NAME}")
    fun retrieveUsers(): Flow<List<User>>

    @Query("SELECT * FROM ${Constant.HANA_TABLE_NAME} WHERE id == :id")
    suspend fun getUserById(id: Long): User?

    @Query("SELECT * FROM ${Constant.HANA_TABLE_NAME} WHERE id == :id")
    fun retrieveUserByIdViaFlow(id: Int): Flow<User>

    @Update
    suspend fun updateUser(movie: User): Int

    @Query("DELETE FROM ${Constant.HANA_TABLE_NAME}")
    suspend fun deleteAllUsers()
}