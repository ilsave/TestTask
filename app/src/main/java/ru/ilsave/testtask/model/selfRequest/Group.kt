package ru.ilsave.testtask.model.selfRequest


import com.google.gson.annotations.SerializedName

data class Group(
    val id: String,
    val manager: String,
    val name: String
)