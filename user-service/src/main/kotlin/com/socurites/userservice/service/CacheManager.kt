package com.socurites.userservice.service

import com.socurites.userservice.exception.UserNotFoundException
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

    suspend fun awaitEvict(token: String) {
        localCache.remove(token)
    }

    suspend fun awaitGetOrPut(key: String, ttl: Duration, supplier: suspend () -> T): T {
        var cached = localCache[key] ?: throw UserNotFoundException()

        if ( Instant.now().isAfter(cached.ttl)) {
            awaitEvict(key)
            awaitPut(key, supplier(), ttl)
        }
        cached = localCache[key]!!


        checkNotNull(cached.cached)
        return cached.cached
    }

    data class CacheWrapper<T>(
        val cached: T,
        val ttl: Instant,
    )
}