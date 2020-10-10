package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class commonResponse(
    val response: Response,
    val status: Int
): Serializable