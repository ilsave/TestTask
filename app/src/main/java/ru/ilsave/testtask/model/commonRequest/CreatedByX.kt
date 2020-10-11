package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreatedByX(
    val avatarSmall: String?,
    val displayName: String?,
    val id: String?,
    val profileUrl: String?
): Serializable