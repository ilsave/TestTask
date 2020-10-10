package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName

data class commonResponse(
    val count: Int,
    val response: Response,
    val status: Int,
    val statusCode: Int
)