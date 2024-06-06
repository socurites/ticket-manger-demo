package com.socurites.userservice.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

/**
 * jwt 토큰 관리 임시
 */
@Component
class CacheManager<T> {
    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()

    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache.put(key, CacheWrapper(cached = value, ttl=Instant.now().plusMillis(ttl.toMillis())))
    }

    data class CacheWrapper<T>(
        val cached: T,
        val ttl: Instant,
    )
}