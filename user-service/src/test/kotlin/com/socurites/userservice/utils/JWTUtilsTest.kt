package com.socurites.userservice.utils

import com.socurites.userservice.config.JWTProperties
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class JWTUtilsTest {
    private val logger = KotlinLogging.logger {  }

    @Test
    fun createTokenTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "socurites@gmail.com",
            username = "socurites",
            profileUrl = "profile.png"
        )

        val jwtProperties = JWTProperties(
            issuer = "tmd",
            subject = "auth",
            expiresSeconds = 3600,
            secret = "my-secret"
        )

        val jwtToken = JWTUtils.createToken(jwtClaim, jwtProperties)

        assertNotNull(jwtToken)

        logger.info { "jwtToken: $jwtToken" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "socurites@gmail.com",
            username = "socurites",
            profileUrl = "profile.png"
        )

        val jwtProperties = JWTProperties(
            issuer = "tmd",
            subject = "auth",
            expiresSeconds = 3600,
            secret = "my-secret"
        )

        val jwtToken = JWTUtils.createToken(jwtClaim, jwtProperties)

        val decodedJWT = JWTUtils.decode(jwtToken, jwtProperties.secret, jwtProperties.issuer)

        with(decodedJWT) {
            logger.info { "claims: $claims" }

            assertEquals(jwtClaim.userId, claims["userId"]!!.asLong())
            assertEquals(jwtClaim.email, claims["email"]!!.asString())
            assertEquals(jwtClaim.username, claims["username"]!!.asString())
            assertEquals(jwtClaim.profileUrl, claims["profileUrl"]!!.asString())
        }
    }
}