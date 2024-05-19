package com.socurites.issueservice.service

import com.socurites.issueservice.domain.Issue
import com.socurites.issueservice.domain.IssueRepository
import com.socurites.issueservice.service.model.IssueRequest
import com.socurites.issueservice.service.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issueRepository.save(issue))
    }
}