package com.socurites.issueservice.service

import com.socurites.issueservice.domain.Issue
import com.socurites.issueservice.domain.IssueRepository
import com.socurites.issueservice.domain.IssueStatus
import com.socurites.issueservice.exception.NotFoundException
import com.socurites.issueservice.model.IssueRequest
import com.socurites.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
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

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus) = issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
        ?.map { IssueResponse(it) }

    @Transactional(readOnly = true)
    fun get(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")
        return IssueResponse(issue)
    }

    @Transactional
    fun update(userId: Long, id: Long, request: IssueRequest): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")

        return with(issue) {
            summary = request.summary
            description = request.description
            this.userId = userId
            type = request.type
            priority = request.priority
            status = request.status

            IssueResponse(issueRepository.save(this))
        }
    }

    @Transactional
    fun delete(id: Long) = issueRepository.deleteById(id)
}