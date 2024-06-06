package com.socurites.userservice.service

import com.socurites.userservice.config.JWTProperties
import com.socurites.userservice.domain.User
import com.socurites.userservice.domain.repository.UserRepository
import com.socurites.userservice.exception.PassswordNotMatchedException
import com.socurites.userservice.exception.UserExistsException
import com.socurites.userservice.exception.UserNotFoundException
import com.socurites.userservice.model.SignInRequest
import com.socurites.userservice.model.SignInResponse
import com.socurites.userservice.model.SignUpRequest
import com.socurites.userservice.utils.EncryptUtils
import com.socurites.userservice.utils.JWTClaim
import com.socurites.userservice.utils.JWTUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CacheManager<User>,
) {
    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

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

    suspend fun siginIn(request: SignInRequest): SignInResponse {
        with(withContext(Dispatchers.IO) {
            userRepository.findByEmail(request.email)
        } ?: throw UserNotFoundException()) {
            val verified = EncryptUtils.verify(request.password, password)
            if (!verified) {
                throw PassswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username,
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)
            cacheManager.awaitPut(key = token, value = this.copy().apply { password = "" }, ttl = CACHE_TTL)

            return SignInResponse(
                email = email,
                username = username,
                token = token,
            )
        }
    }

    suspend fun signOut(token: String) {
        cacheManager.awaitEvict(token)
    }
}