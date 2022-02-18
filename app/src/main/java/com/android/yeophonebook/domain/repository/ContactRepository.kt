package com.android.yeophonebook.domain.repository

import com.android.yeophonebook.data.entity.ContactEntity
import com.android.yeophonebook.domain.model.ContactDomain
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun insert(contacts: List<ContactDomain>)
    fun get() : Flow<List<ContactDomain>>
    suspend fun get(id: Long): ContactEntity
}