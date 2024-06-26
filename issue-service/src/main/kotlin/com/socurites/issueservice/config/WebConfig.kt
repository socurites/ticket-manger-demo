package com.socurites.issueservice.config

import com.fasterxml.jackson.annotation.JsonProperty
import com.socurites.issueservice.exception.UnauthorizedException
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.lang.Exception

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
) : WebMvcConfigurationSupport() {
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(argumentResolvers)

        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }
}

@Component
class AuthUserHandlerArgumentResolver(
    @Value("\${service.auth.url}") val serviceAuthUrl: String,
): HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authHeader = webRequest.getHeader("Authorization") ?: throw UnauthorizedException()

        return runBlocking {
            try {
                WebClient.create()
                    .get()
                    .uri(serviceAuthUrl)
                    .header("Authorization", authHeader)
                    .retrieve()
                    .awaitBody<AuthUser>()
            } catch (e: Exception) {
                throw UnauthorizedException()
            }
        }
    }

}

data class AuthUser (
    @JsonProperty("id")
    val userId: Long,
    val username: String,
    val email: String,
    val profileUrl: String? = null,
)