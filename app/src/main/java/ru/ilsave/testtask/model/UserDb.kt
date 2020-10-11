package ru.ilsave.testtask.model

import androidx.room.Entity
import java.io.Serializable


data class UserDb(
    val userPortal: String,
    val userToken: String
): Serializable {
}