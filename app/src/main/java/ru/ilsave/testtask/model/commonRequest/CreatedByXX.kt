package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName

data class CreatedByXX(
    val avatarSmall: String,
    val displayName: String,
    val id: String,
    val profileUrl: String
)