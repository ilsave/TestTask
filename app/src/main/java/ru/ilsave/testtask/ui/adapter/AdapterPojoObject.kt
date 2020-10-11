package ru.ilsave.testtask.ui.adapter

data class AdapterPojoObject (
    val name: String,
    val isFolder: Boolean,
    val isFile: Boolean,
    val date: String,
    val size: String,
    val folderId: String
)
