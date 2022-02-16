package com.android.yeophonebook.domain.model

import androidx.room.PrimaryKey

data class ContactDomain(
    @PrimaryKey
    val id: String,
    val name: String,
    val phoneNumber: String
)
