package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName

data class Response(
    val count: Int,
    val current: Current,
    val files: List<File>,
    val folders: List<Folder>,
    val pathParts: PathParts,
    val startIndex: Int,
    val total: Int
)