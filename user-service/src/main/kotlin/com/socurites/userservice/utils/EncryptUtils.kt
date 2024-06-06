package com.socurites.userservice.utils

import at.favre.lib.crypto.bcrypt.BCrypt

object EncryptUtils {
    fun hash(original: String) =
        BCrypt.withDefaults().hashToString(12, original.toCharArray())

    fun verify(original: String, hashed: String) =
        BCrypt.verifyer().verify(original.toCharArray(), hashed).verified
}