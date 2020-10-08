package ru.ilsave.testtask.model


import com.google.gson.annotations.SerializedName

data class ResponseX(
    val activationStatus: Int,
    val avatar: String,
    val avatarMedium: String,
    val avatarSmall: String,
    val birthday: String,
    val contacts: List<Contact>,
    val cultureName: String,
    val department: String,
    val displayName: Any,
    val email: String,
    val firstName: String,
    val groups: List<Group>,
    val id: String,
    val isAdmin: Boolean,
    val isLDAP: Boolean,
    val isOwner: Boolean,
    val isSSO: Boolean,
    val isVisitor: Boolean,
    val lastName: String,
    val listAdminModules: List<String>,
    val location: String,
    val notes: String,
    val profileUrl: String,
    val sex: String,
    val status: Int,
    val terminated: String,
    val title: String,
    val userName: String,
    val workFrom: String
)