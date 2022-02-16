package com.android.yeophonebook.domain.repository

import com.android.yeophonebook.domain.model.ContactDomain
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun insert(contactDomain: ContactDomain)
    suspend fun insert(contacts: List<ContactDomain>)
    fun get() : Flow<List<ContactDomain>>
}