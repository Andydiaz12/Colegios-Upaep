package com.upaep.colegios.data.base

import com.upaep.colegios.data.entities.upaepservices.UpaepStandardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ColegiosInterface {
    @GET("06b7aa7a-5a87-434c-9482-15e11a79dab5")
    suspend fun doLogin(): Response<UpaepStandardResponse>

    @POST("general/general/parku/")
    suspend fun testHeader() : Response<UpaepStandardResponse>
}