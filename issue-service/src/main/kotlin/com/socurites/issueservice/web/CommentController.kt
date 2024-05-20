package com.socurites.issueservice.web

import com.socurites.issueservice.config.AuthUser
import com.socurites.issueservice.model.CommentRequest
import com.socurites.issueservice.model.CommentResponse
import com.socurites.issueservice.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest,
    ): CommentResponse = commentService.create(issueId, authUser.userId, authUser.username, request)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: CommentRequest,
    ) = commentService.update(id, authUser.userId, request)
}