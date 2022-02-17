package com.android.yeophonebook.domain.repository

import com.android.yeophonebook.data.dao.ContactDao
import com.android.yeophonebook.data.db.ContactDataBase
import com.android.yeophonebook.data.entity.ContactEntity
import com.android.yeophonebook.domain.ContactMapper
import com.android.yeophonebook.domain.model.ContactDomain
import com.android.yeophonebook.ui.model.Contact
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val contactMapper: ContactMapper
) : ContactRepository {
    companion object {
        private const val INSERT_FAILURE_ID = -1L
    }
    override suspend fun insert(contacts: List<ContactDomain>) {
        contacts.forEach {
            val contact = contactMapper.mapToEntity(it).apply { dateUpdated = System.currentTimeMillis() }
            val insertId = contactDao.addContactToDb(contact)
            if (insertId == INSERT_FAILURE_ID) {
                update(contact)
            }
        }
    }

    override suspend fun update(contact: ContactEntity) {
        val contactInDb = get(contact.id)
        if(contact.equals(contactInDb).not()){
            contactDao.update(contact.apply { dateUpdated = System.currentTimeMillis() })
        }
    }

    override fun get(): Flow<List<ContactDomain>> =
        contactDao.getAll().map {
            it.map { entity ->
                contactMapper.mapToDomain(entity)
            }
        }

    override fun get(id: Long): ContactEntity =
        contactDao.getContactById(id)
}