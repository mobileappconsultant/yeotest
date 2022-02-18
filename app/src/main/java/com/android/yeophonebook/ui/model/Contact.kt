package com.android.yeophonebook.ui.model

data class Contact(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val dateUpdated: String? = null
)
