package com.socurites.issueservice.exception

data class ErrorResponse (
    val code: Int,
    val message: String,
)