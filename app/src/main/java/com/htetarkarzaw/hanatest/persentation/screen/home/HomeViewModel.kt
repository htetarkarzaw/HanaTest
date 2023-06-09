package com.htetarkarzaw.hanatest.persentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.domain.usecase.FetchUsersUsecase
import com.htetarkarzaw.hanatest.domain.usecase.RetrieveUsersUsecase
import com.htetarkarzaw.hanatest.domain.vo.UserVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUsersUsecase: FetchUsersUsecase,
    private val retrieveUsersUsecase: RetrieveUsersUsecase
) : ViewModel() {
    private val _users = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val users get() = _users.asStateFlow()

    private val _dbUsers = MutableStateFlow<List<UserVO>>(emptyList())
    val dbUsers get() = _dbUsers.asStateFlow()
    init {
        retrieveUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _users.value = Resource.Loading()
            fetchUsersUsecase().collectLatest {
                _users.value = it
            }
        }
    }

    private fun retrieveUsers() {
        viewModelScope.launch {
            retrieveUsersUsecase().collectLatest {
                _dbUsers.value = it
            }
        }
    }
}