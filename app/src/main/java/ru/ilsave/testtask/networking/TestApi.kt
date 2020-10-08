package ru.ilsave.testtask.networking

import retrofit2.Response
import retrofit2.http.*
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.UserSefRequest
import ru.ilsave.testtask.model.apiResponse

interface TestApi {

    @POST("https://ilsave.onlyoffice.eu/api/2.0/authentication")
    suspend fun pushUser1(
        @Body user: User
    ): Response<User>


    @Headers("Content-Type: application/json",
                    "Accept: application/json")
    @POST("https://{yourportal}.onlyoffice.eu/api/2.0/authentication")
    suspend fun pushUser2(
        @Header("Host") myHost: String,
        @Path("yourportal") myportal: String,
        @Body user: User
    ): Response<apiResponse>


    @Headers("Content-Type: application/json",
        "Accept: application/json")
    @GET("https://{yourportal}.onlyoffice.eu/api/2.0/people/@self")
     suspend fun getUserInfo(
        @Header("Host") myHost: String,
        @Header("Cookie") ascAuthKey: String,
        @Path("yourportal") myportal: String,
    ):Response<UserSefRequest>


    @GET("https://{yourportal}.onlyoffice.eu/api/2.0/authentication")
    suspend fun getPhoto(
        @Header("Cookie") ascAuthKey :String,
        @Path("yourportal") myportal: String,

    ): String
}