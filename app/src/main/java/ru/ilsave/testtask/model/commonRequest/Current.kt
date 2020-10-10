package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName

data class Current(
    val access: Int,
    val created: String,
    val createdBy: CreatedBy,
    val filesCount: Int,
    val foldersCount: Int,
    val id: Int,
    val isShareable: Boolean,
    val parentId: Int,
    val rootFolderType: Int,
    val shared: Boolean,
    val title: String,
    val updated: String,
    val updatedBy: UpdatedBy
)