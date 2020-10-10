package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Folder(
    val access: Int,
    val created: String,
    val createdBy: CreatedByXX,
    val filesCount: Int,
    val foldersCount: Int,
    val id: Int,
    val parentId: Int,
    val rootFolderType: Int,
    val shared: Boolean,
    val title: String,
    val updated: String,
    val updatedBy: UpdatedByXX
): Serializable