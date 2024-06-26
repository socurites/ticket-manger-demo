package com.socurites.issueservice.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.socurites.issueservice.domain.*
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

data class IssueResponse(
    val id: Long,
    val summary: String,
    val description: String,
    val userId: Long,
    val comments: List<CommentResponse> = emptyList(),
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(issue: Issue) =
            IssueResponse(
                id = issue.id!!,
                summary = issue.summary,
                description = issue.description,
                userId = issue.userId,
                comments = issue.comments.sortedByDescending(Comment::id).map(Comment::toResponse),
                type = issue.type,
                priority = issue.priority,
                status = issue.status,
                createdAt = issue.createdAt,
                updatedAt = issue.updatedAt,
            )
    }
}