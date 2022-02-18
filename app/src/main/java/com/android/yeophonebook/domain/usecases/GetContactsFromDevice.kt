package com.android.yeophonebook.domain.usecases

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.android.yeophonebook.ui.model.Contact
import com.android.yeophonebook.utils.isValid
import javax.inject.Inject
import timber.log.Timber


class GetContactsFromDevice @Inject constructor() {

    companion object {
        private val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
    }

     fun getContactList(context: Context) : List<Contact> {
        val contactList: MutableList<Contact> = mutableListOf()
        val cr: ContentResolver = context.contentResolver
        val cursor: Cursor? = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor != null) {
            val mobileNoSet = HashSet<String>()
            cursor.use {
                val idIndex: Int = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                val nameIndex: Int = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val numberIndex: Int =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                var name: String
                var number: String
                var id: Long
                while (it.moveToNext()) {
                    name = it.getString(nameIndex)
                    number = it.getString(numberIndex)
                    id = it.getLong(idIndex)
                    number = number.replace(" ", "")
                    if (mobileNoSet.contains(number).not() && number.isValid() ) {
                        contactList.add(Contact(id, name, number))
                        mobileNoSet.add(number)
                        Timber.d("Contact: name = $name No = $number")
                    }
                }
            }
        }
        return contactList
    }

}