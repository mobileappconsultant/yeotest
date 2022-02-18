package com.android.yeophonebook.ui.viewmodel

import android.content.Context
import com.android.yeophonebook.TestCoroutineRule
import com.android.yeophonebook.domain.ContactMapper
import com.android.yeophonebook.domain.model.ContactDomain
import com.android.yeophonebook.domain.usecases.FetchContactsFromDbUseCase
import com.android.yeophonebook.domain.usecases.GetContactsFromDevice
import com.android.yeophonebook.domain.usecases.SaveContactsToDbUseCase
import com.android.yeophonebook.ui.ContactViewModel
import com.android.yeophonebook.ui.model.Contact
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var sut: ContactViewModel
    private val fetchContactsFromDbUseCase = mockk<FetchContactsFromDbUseCase>()
    private val saveContactsToDbUseCase = mockk<SaveContactsToDbUseCase>()
    private val mockedContact = Contact(id = 10L, name = "Tes5", phoneNumber = "+122331234")
    private val getContactsFromDevice = mockk<GetContactsFromDevice>()
    private val contactMapper = ContactMapper()

    private val mockedContext = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        sut = ContactViewModel(
            contactMapper = contactMapper,
            fetchContactsFromDbUseCase = fetchContactsFromDbUseCase,
            saveContactsToDbUseCase = saveContactsToDbUseCase,
            getContactsFromDevice = getContactsFromDevice
        )
    }

    @Test
    fun `given when user syncs contacts from the device, then contacts on the device save contacts to db should be called`() =
        testCoroutineRule.runTest {
            every {
                getContactsFromDevice.getContactList(any())
            } returns listOf(mockedContact)
            sut.getContactsFromDevice(mockedContext)
            coVerify {
                saveContactsToDbUseCase.execute(any())
            }
        }

    @Test
    fun `given when onInit is called, verify that fetchDataFromDb Use case is executed`() =
        testCoroutineRule.runTest {
            coEvery {
                fetchContactsFromDbUseCase.execute()
            } returns flowOf(listOf(ContactDomain(12, "name", "082288222", 122292922)))
            sut.onInit()
            coVerify {
                fetchContactsFromDbUseCase.execute()
            }
        }
}
