package com.htetarkarzaw.hanatest.data.remote

import retrofit2.Response

sealed class RemoteResource<T>(
    val data: T? = null,
    val message: CustomMessages = CustomMessages.SomethingWentWrong("Something Went Wrong")
) {
    class LoadingEvent<T> : RemoteResource<T>()
    class SuccessEvent<T>(data: T) : RemoteResource<T>(data)
    class ErrorEvent<T>(errorMessage: CustomMessages) : RemoteResource<T>(null, errorMessage)

    sealed class CustomMessages(val message: String = "") {
        object Timeout : CustomMessages()
        object EmptyData : CustomMessages()
        object ServerError : CustomMessages()
        object HttpException : CustomMessages()
        object SocketTimeOutException : CustomMessages()
        object NoInternet : CustomMessages()
        object Unauthorized : CustomMessages()
        object InternalServerError : CustomMessages()
        object BadRequest : CustomMessages()
        object Conflict : CustomMessages()
        object NotFound : CustomMessages()
        object NotAcceptable : CustomMessages()
        object ServiceUnavailable : CustomMessages()
        object Forbidden : CustomMessages()
        data class SomethingWentWrong(val error: String) : CustomMessages(message = error)
    }

    fun getErrorMessage(): String {
        return when (message) {
            is CustomMessages.BadRequest -> message.message
            CustomMessages.Conflict -> "Conflict"
            CustomMessages.EmptyData -> "Empty Data"
            CustomMessages.Forbidden -> "Forbidden"
            CustomMessages.HttpException -> "HttpException"
            CustomMessages.InternalServerError -> "Internal Server Error"
            CustomMessages.NoInternet -> "No Internet"
            CustomMessages.NotAcceptable -> "NotAcceptable"
            CustomMessages.NotFound -> message.message
            CustomMessages.ServerError -> "Server Error"
            CustomMessages.ServiceUnavailable -> "Service Unavailable"
            CustomMessages.SocketTimeOutException -> "SocketTimeOutException"
            is CustomMessages.SomethingWentWrong -> "Something Went Wrong"
            CustomMessages.Timeout -> "Timeout"
            CustomMessages.Unauthorized -> "Unauthorized"
        }
    }
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): RemoteResource<T> {
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body() ?: return RemoteResource.ErrorEvent(RemoteResource.CustomMessages.EmptyData)
            return RemoteResource.SuccessEvent(body)
        }
        return RemoteResource.ErrorEvent(
            handleException(response.code(), response.errorBody().toString())
        )
    } catch (e: Exception) {
        return RemoteResource.ErrorEvent(handleException(e))
    }
}