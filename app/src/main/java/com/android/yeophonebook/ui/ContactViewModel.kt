package com.android.yeophonebook.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.yeophonebook.domain.ContactMapper
import com.android.yeophonebook.domain.usecases.FetchContactsFromDbUseCase
import com.android.yeophonebook.domain.usecases.GetContactsFromDevice
import com.android.yeophonebook.domain.usecases.SaveContactsToDbUseCase
import com.android.yeophonebook.ui.model.Contact
import com.android.yeophonebook.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactMapper: ContactMapper,
    private val fetchContactsFromDbUseCase: FetchContactsFromDbUseCase,
    private val saveContactsToDbUseCase: SaveContactsToDbUseCase,
    private val getContactsFromDevice: GetContactsFromDevice
) : ViewModel() {

    private val _contactsLiveData = MutableLiveData<List<Contact>>(listOf())

    val contactsLiveData = _contactsLiveData.asLiveData()

    private fun saveContactsToDb(contact: List<Contact>) {
        viewModelScope.launch {
            saveContactsToDbUseCase
                .execute(contact.map { contactMapper.mapToDomain(it) })
                .collect()
        }
    }

    fun onInit() {
        viewModelScope.launch {
            fetchContactsFromDbUseCase
                .execute()
                .collect {
                    _contactsLiveData.postValue(it.map { contact ->
                        contactMapper.mapToPresentation(contact)
                    })
                }
        }
    }

    fun getContactsFromDevice(context: Context) {
        viewModelScope.launch {
            val contacts = getContactsFromDevice.getContactList(context)
            saveContactsToDb(contacts)
        }
    }
}