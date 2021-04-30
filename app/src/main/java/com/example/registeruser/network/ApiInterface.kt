package com.example.registeruser.network

import com.example.registeruser.models.BaseResponse
import com.example.registeruser.models.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
interface ApiInterface {
    @POST("/v2/user")
    suspend fun registerUser(
        @Body userData: UserData
    ): Response<BaseResponse>

    @GET("/v2/user/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<UserData>
}