package com.upaep.colegios.data.base.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import javax.inject.Inject
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton

@Singleton
class MyServiceInterceptor @Inject constructor() : Interceptor {
    private var authorization: String = ""
    private var baseUrl: String? = null

    fun setAuthorization(authorization: String) {
        this.authorization = authorization
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