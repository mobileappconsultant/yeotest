package com.android.yeophonebook.domain.model

import androidx.room.PrimaryKey

data class ContactDomain(
    @PrimaryKey
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val dateUpdated: Long? = null
)
