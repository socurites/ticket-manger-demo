package com.socurites.userservice.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.socurites.userservice.config.JWTProperties
import java.util.*

object JWTUtils {
    fun createToken(claim: JWTClaim, properties: JWTProperties): String =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresSeconds * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("email", claim.email)
            .withClaim("username", claim.username)
            .withClaim("profileUrl", claim.profileUrl)
            .sign(Algorithm.HMAC256(properties.secret))

    fun decode(token: String, secret: String, issuer: String): DecodedJWT {
        val jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()

        return jwtVerifier.verify(token)
    }
}

data class JWTClaim(
    val userId: Long,
    val email: String,
    val username: String,
    val profileUrl: String,
)