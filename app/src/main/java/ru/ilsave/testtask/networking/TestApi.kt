package ru.ilsave.testtask.networking

import retrofit2.Response
import retrofit2.http.*
import ru.ilsave.testtask.model.User
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

}