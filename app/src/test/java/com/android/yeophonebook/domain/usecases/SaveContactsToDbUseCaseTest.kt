package com.android.yeophonebook.domain.usecases

import com.android.yeophonebook.TestCoroutineRule
import com.android.yeophonebook.domain.repository.ContactRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveContactsToDbUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val contactRepository: ContactRepository = mockk<ContactRepository>(relaxed = true).apply {
        coEvery {
            insert(any())
        } returns Unit
    }

    private val sut: SaveContactsToDbUseCase =
        SaveContactsToDbUseCase(contactRepository)

    @Test
    fun `given when saveContactsToDbUseCase is executed, verify that contactRepository insert is called `() = testCoroutineRule.runTest {
        sut.execute(listOf())
        coVerify {
            contactRepository.insert(any())
        }
    }
}
