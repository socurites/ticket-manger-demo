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

    @Transactional
    fun update(id: Long, userId: Long, request: CommentRequest): CommentResponse {
        val comment = commentRepository.findByIdAndUserId(id, userId) ?: throw NotFoundException("댓글이 존재하지 않습니다")

        return comment.run {
            body = request.body
            commentRepository.save(this).toResponse()
        }
    }

    @Transactional
    fun delete(issueId: Long, id: Long, userId: Long) {
        val comment = commentRepository.findByIdAndUserId(id, userId) ?: throw NotFoundException("댓글이 존재하지 않습니다")
        // 아래는 에러 발생
        // error:  SQL [delete from comment where id=?]; constraint ["FKTJG467PSBEI20VCI3SFN9IAK: PUBLIC.ISSUE_COMMENTS FOREIGN KEY(COMMENTS_ID) REFERENCES PUBLIC.COMMENT(ID) (CAST(1 AS BIGINT))"
        // commentRepository.deleteById(id)

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("이슈가 존재하지 않습니다")
        issue.comments.remove(comment)

        commentRepository.deleteById(id)
    }
}