package com.htetarkarzaw.hanatest.persentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import com.htetarkarzaw.hanatest.domain.usecase.CreateJsonUsecase
import com.htetarkarzaw.hanatest.domain.usecase.UploadUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uploadUserUsecase: UploadUserUsecase,
    private val createJsonUsecase: CreateJsonUsecase
) : ViewModel() {
    private val _uploadUser = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val uploadUser get() = _uploadUser.asStateFlow()

    private val _createJson = MutableStateFlow<Resource<String>>(Resource.Nothing())
    val createJson get() = _createJson.asStateFlow()

    fun uploadUser(userId: String, title: String, body: String) {
        _uploadUser.value = Resource.Loading()
        viewModelScope.launch {
            uploadUserUsecase(PostModelCriteria(userId = userId, title = title, body = body)).collectLatest {
                _uploadUser.value = it
            }
        }
    }

    fun createJson(file:File){
        _createJson.value = Resource.Loading()
        viewModelScope.launch {
            createJsonUsecase(file = file).collectLatest {
                _createJson.value = it
            }
        }
    }
}