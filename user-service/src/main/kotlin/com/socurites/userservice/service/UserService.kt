package com.socurites.userservice.service

import com.socurites.userservice.domain.User
import com.socurites.userservice.domain.repository.UserRepository
import com.socurites.userservice.exception.UserExistsException
import com.socurites.userservice.model.SignUpRequest
import com.socurites.userservice.utils.EncryptUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = EncryptUtils.hash(password),
                username = username,
            )

            userRepository.save(user)
        }
    }
}