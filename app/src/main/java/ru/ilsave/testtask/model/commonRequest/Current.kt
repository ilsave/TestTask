package ru.ilsave.testtask.model.commonRequest


import com.google.gson.annotations.SerializedName

data class Current(
    val access: Int,
    val created: String,
    val createdBy: CreatedBy,
    val filesCount: Int,
    val foldersCount: Int,
    val id: Int,
    val parentId: Int,
    val providerItem: Boolean,
    val rootFolderType: Int,
    val shared: Boolean,
    val title: String,
    val updated: String,
    val updatedBy: UpdatedBy
)