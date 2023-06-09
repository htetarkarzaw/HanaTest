package com.htetarkarzaw.hanatest.persentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.remote.dto.UsersDTO
import com.htetarkarzaw.hanatest.domain.usecase.FetchUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fetchUsersUsecase: FetchUsersUsecase) : ViewModel() {
    private val _users = MutableStateFlow<Resource<UsersDTO>>(Resource.Nothing())
    val users get() = _users.asStateFlow()
    fun fetchUser(){
        viewModelScope.launch {
            fetchUsersUsecase().collectLatest {
                _users.value = it
            }
        }
    }
}