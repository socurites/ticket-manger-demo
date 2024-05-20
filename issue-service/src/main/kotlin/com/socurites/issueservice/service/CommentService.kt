package com.socurites.issueservice.service

import com.socurites.issueservice.domain.Comment
import com.socurites.issueservice.domain.CommentRepository
import com.socurites.issueservice.domain.IssueRepository
import com.socurites.issueservice.exception.NotFoundException
import com.socurites.issueservice.model.CommentRequest
import com.socurites.issueservice.model.CommentResponse
import com.socurites.issueservice.model.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(issueId: Long, userId: Long, username: String, request: CommentRequest): CommentResponse {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("이슈가 존재하지 않습니다")

        val comment = Comment(
            issue = issue,
            userId = userId,
            username = username,
            body = request.body,
        )

        issue.comments.add(comment)
        return commentRepository.save(comment).toResponse()
    }
}