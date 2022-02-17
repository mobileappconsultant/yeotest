package com.android.yeophonebook.domain

import com.android.yeophonebook.data.entity.ContactEntity
import com.android.yeophonebook.domain.model.ContactDomain
import com.android.yeophonebook.ui.model.Contact
import com.android.yeophonebook.utils.DateUtils.formatMillisToDateString
import javax.inject.Inject

class ContactMapper @Inject constructor() {
    fun mapToDomain(contactEntity: ContactEntity): ContactDomain = ContactDomain(
        contactEntity.id,
        contactEntity.name,
        contactEntity.phoneNumber,
        contactEntity.dateUpdated
    )

    fun mapToEntity(contactDomain: ContactDomain): ContactEntity = ContactEntity(
        contactDomain.id,
        contactDomain.name,
        contactDomain.phoneNumber
    )

    fun mapToPresentation(contactDomain: ContactDomain): Contact = Contact(
        contactDomain.id,
        contactDomain.name,
        contactDomain.phoneNumber,
        contactDomain.dateUpdated?.formatMillisToDateString()
    )

    fun mapToDomain(contact: Contact): ContactDomain = ContactDomain(
        contact.id,
        contact.name,
        contact.phoneNumber
    )
}