package com.htetarkarzaw.hanatest.data.remote

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    BadRequest(400),
    NotFound(404),
    Conflict(409),
    InternalServerError(500),
    Forbidden(403),
    NotAcceptable(406),
    ServiceUnavailable(503),
    UnAuthorized(401),
}

fun handleException(throwable: Exception): RemoteResource.CustomMessages {
    return when (throwable) {
        is HttpException -> RemoteResource.CustomMessages.HttpException
        is TimeoutException -> RemoteResource.CustomMessages.Timeout
        is UnknownHostException -> RemoteResource.CustomMessages.ServerError
        is ConnectException -> RemoteResource.CustomMessages.NoInternet
        is SocketTimeoutException -> RemoteResource.CustomMessages.SocketTimeOutException
        else -> RemoteResource.CustomMessages.NoInternet
    }
}


fun handleException(statusCode: Int, message: String): RemoteResource.CustomMessages {
    return getErrorType(statusCode, message)
}

private fun getErrorType(code: Int, message: String): RemoteResource.CustomMessages {
    return when (code) {
        ErrorCodes.SocketTimeOut.code -> RemoteResource.CustomMessages.Timeout
        ErrorCodes.UnAuthorized.code -> RemoteResource.CustomMessages.Unauthorized
        ErrorCodes.InternalServerError.code -> RemoteResource.CustomMessages.InternalServerError

        ErrorCodes.BadRequest.code -> RemoteResource.CustomMessages.BadRequest
        ErrorCodes.Conflict.code -> RemoteResource.CustomMessages.Conflict
        ErrorCodes.InternalServerError.code -> RemoteResource.CustomMessages.InternalServerError

        ErrorCodes.NotFound.code -> RemoteResource.CustomMessages.NotFound
        ErrorCodes.NotAcceptable.code -> RemoteResource.CustomMessages.NotAcceptable
        ErrorCodes.ServiceUnavailable.code -> RemoteResource.CustomMessages.ServiceUnavailable
        ErrorCodes.Forbidden.code -> RemoteResource.CustomMessages.Forbidden
        else -> RemoteResource.CustomMessages.SomethingWentWrong(message)
    }
}
