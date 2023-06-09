package com.htetarkarzaw.hanatest.domain.vo

data class UserVO(
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String,
    val address: String,
    val company: String,
    val email: String,
){
    companion object{
        fun emptyVO(): UserVO = UserVO(
            id = 0,
            name = "name",
            phone = "phone", username = "username",
            website = "website", address = "address",
            company = "company", email = "email"
        )
    }
}
