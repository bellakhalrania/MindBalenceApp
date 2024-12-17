package com.example.login_signuppi

import iset.example.mindbalenceapp.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/register")
    suspend fun register(@Body user: User): Response<User>
    @POST("/login")
    suspend fun login(@Body user: User): Response<String>
}