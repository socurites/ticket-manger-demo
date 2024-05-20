package com.socurites.issueservice.model

import com.socurites.issueservice.domain.Comment

data class CommentRequest (
    val body: String,
)

data class CommentResponse (
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val username: String,
    val body: String,
)


fun Comment.toResponse() = CommentResponse(
    id = id!!,
    issueId = issue.id!!,
    userId = issue.userId,
    username = username,
    body = body,
)