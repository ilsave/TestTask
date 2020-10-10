package ru.ilsave.testtask.model.afterLogin


import com.google.gson.annotations.SerializedName

data class Response(
    val expires: String,
    val token: String
)