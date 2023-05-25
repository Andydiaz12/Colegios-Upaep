package com.upaep.colegios.data.base

import com.upaep.colegios.data.entities.studentselector.GetStudentModel
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardRequest
import com.upaep.colegios.data.entities.upaepservices.UpaepStandardResponse
import retrofit2.Response
import retrofit2.http.*

interface ColegiosInterface {
    @POST("students/general/colleges/")
    suspend fun getStudents(@Body cryptdata: UpaepStandardRequest): Response<UpaepStandardResponse>

    @POST
    suspend fun getLocksmith(@Body cryptdata: UpaepStandardRequest, @Url url: String): Response<UpaepStandardResponse>

    @GET
    suspend fun getFeatures(): Response<UpaepStandardResponse>

    @GET
    suspend fun doLogin(): Response<UpaepStandardResponse>

    @GET("students/general/colleges/grades/")
    suspend fun getGrades(@Query("CRYPTDATA") cryptData: UpaepStandardRequest): Response<UpaepStandardResponse>

    @GET("students/general/colleges/schedule/")
    suspend fun getSchedule(@Query("CRYPTDATA") cryptData: String): Response<UpaepStandardResponse>
}