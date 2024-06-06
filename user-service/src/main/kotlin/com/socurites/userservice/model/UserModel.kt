package com.socurites.userservice.model

import com.socurites.userservice.domain.User
import java.time.LocalDateTime


data class MeResponse(
    val id: Long,
    val profileUrl: String?,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(user: User): MeResponse {
            return with(user) {
                MeResponse(
                    id = id!!,
                    profileUrl = profileUrl,
                    username = username,
                    email = email,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
        }
    }
}