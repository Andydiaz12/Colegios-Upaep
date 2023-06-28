package com.upaep.colegios.model.base.retrofit

import com.upaep.colegios.model.base.jwt.JwtHelper
import com.upaep.colegios.model.entities.locksmith.IDDKeychain
import okhttp3.Interceptor
import okhttp3.Request
import javax.inject.Inject
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class MyServiceInterceptor @Inject constructor() : Interceptor {
    private var authorization: String = ""
    private var baseUrl: String? = null

    fun setAuthorization(keyChain: IDDKeychain) {
        this.authorization = JwtHelper().createJwt(
            API_KEY = keyChain.JWTKeychain!!.apiKey!!,
            JWT_KEY = keyChain.JWTKeychain!!.jwtKey!!,
            JSON_KEY = keyChain.JWTKeychain?.jsonKey ?: "key"
        )
    }

    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
        requestBuilder.addHeader("Authorization", authorization)
        return chain.proceed(requestBuilder.build())
    }
}