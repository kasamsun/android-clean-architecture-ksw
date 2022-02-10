package com.kswafx.androidclean.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WemoApi {

    @POST("/api/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

}
