package com.socurites.userservice.web

import com.socurites.userservice.model.AuthToken
import com.socurites.userservice.model.SignInRequest
import com.socurites.userservice.model.SignInResponse
import com.socurites.userservice.model.SignUpRequest
import com.socurites.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        userService.signUp(request)
    }

    @PostMapping("/signin")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse {
        return userService.siginIn(request)
    }

    @DeleteMapping("/signout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun signOut(@AuthToken token: String) {
        userService.signOut(token)
    }
}