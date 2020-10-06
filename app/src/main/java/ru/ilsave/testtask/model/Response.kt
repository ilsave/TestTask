package ru.ilsave.testtask.model


import com.google.gson.annotations.SerializedName

data class Response(
    val expires: String,
    val token: String
)