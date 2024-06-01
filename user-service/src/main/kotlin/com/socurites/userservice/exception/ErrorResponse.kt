package com.socurites.userservice.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)