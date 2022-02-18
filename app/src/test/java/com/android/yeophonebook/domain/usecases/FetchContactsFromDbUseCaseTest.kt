package com.android.yeophonebook.domain.usecases

import com.android.yeophonebook.TestCoroutineRule
import com.android.yeophonebook.domain.repository.ContactRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchContactsFromDbUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val contactRepository: ContactRepository = mockk<ContactRepository>(relaxed = true).apply {
        every {
            get()
        } returns flowOf()
    }

    private val sut: FetchContactsFromDbUseCase =
        FetchContactsFromDbUseCase(contactRepository)

    @Test
    fun `given when fetchContactsFromDbUseCase is executed, verify that contactRepository get is called `() = testCoroutineRule.runTest{
        sut.execute()
        verify {
            contactRepository.get()
        }
    }
}