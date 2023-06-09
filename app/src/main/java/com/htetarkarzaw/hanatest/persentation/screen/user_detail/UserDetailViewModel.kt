package com.htetarkarzaw.hanatest.persentation.screen.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.hanatest.domain.usecase.RetrieveUserByIdUsecase
import com.htetarkarzaw.hanatest.domain.vo.UserVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val retrieveUserByIdUsecase: RetrieveUserByIdUsecase
) : ViewModel() {

    private val _dbUser = MutableStateFlow(UserVO.emptyVO())
    val dbUser get() = _dbUser.asStateFlow()

    fun retrieveUserById(userId: Int) {
        viewModelScope.launch {
            retrieveUserByIdUsecase(userId = userId).collectLatest {
                _dbUser.value = it
            }
        }
    }
}