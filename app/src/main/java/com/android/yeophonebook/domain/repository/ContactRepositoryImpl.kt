package com.android.yeophonebook.domain.repository

import com.android.yeophonebook.data.dao.ContactDao
import com.android.yeophonebook.data.db.ContactDataBase
import com.android.yeophonebook.domain.ContactMapper
import com.android.yeophonebook.domain.model.ContactDomain
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val contactMapper: ContactMapper
) : ContactRepository {
    override suspend fun insert(contactDomain: ContactDomain) =
        contactDao.addContactToDb(contactMapper.mapToEntity(contactDomain))

    override suspend fun insert(contacts: List<ContactDomain>) =
        contactDao.addContactsToDb(contacts.map { contactMapper.mapToEntity(it) })

    override fun get(): Flow<List<ContactDomain>> =
        contactDao.getContactsFromDb().map {
            it.map { entity ->
                contactMapper.mapToDomain(entity)
            }
        }
}