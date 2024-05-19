package com.socurites.issueservice.web

import com.socurites.issueservice.config.AuthUser
import com.socurites.issueservice.service.IssueService
import com.socurites.issueservice.service.model.IssueRequest
import com.socurites.issueservice.service.model.IssueResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ): IssueResponse = issueService.create(authUser.userId, request)
}