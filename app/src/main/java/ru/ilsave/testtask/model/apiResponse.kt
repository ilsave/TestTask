package ru.ilsave.testtask.model


import com.google.gson.annotations.SerializedName

data class apiResponse(
    val count: Int,
    val response: Response,
    val status: Int,
    val statusCode: Int
)