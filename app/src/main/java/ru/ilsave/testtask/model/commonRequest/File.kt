package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class File(
    val access: Int,
    val comment: String,
    val contentLength: String,
    val created: String,
    val createdBy: CreatedByX,
    val fileExst: String,
    val fileStatus: Int,
    val fileType: Int,
    val folderId: Int,
    val id: Int,
    val pureContentLength: Int,
    val rootFolderType: Int,
    val shared: Boolean,
    val title: String,
    val updated: String,
    val updatedBy: UpdatedByX,
    val version: Int,
    val versionGroup: Int,
    val viewUrl: String,
    val webUrl: String
): Serializable