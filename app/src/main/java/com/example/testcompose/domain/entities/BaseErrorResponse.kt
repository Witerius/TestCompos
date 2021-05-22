package kz.viled.domain.entities

import java.io.Serializable

open class BaseErrorResponse(
    var code: Any? = null,
    var message: String? = null,
    var errors: Errors? = null,
    val errorCode: String? = null,
) : Serializable

data class Errors(
    val smsCode: String? = null,
    val currentPassword: String? = null
) : Serializable