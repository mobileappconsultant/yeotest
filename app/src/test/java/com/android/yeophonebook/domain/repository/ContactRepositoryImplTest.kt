package com.android.yeophonebook.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.yeophonebook.data.db.ContactDatabase
import com.android.yeophonebook.domain.ContactMapper
import com.android.yeophonebook.domain.model.ContactDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ContactRepositoryImplTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    private lateinit var db: ContactDatabase

    private lateinit var sut: ContactRepository

    private val contactMapper: ContactMapper = ContactMapper()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, ContactDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        sut = ContactRepositoryImpl(contactDao = db.getContactDao(), contactMapper)
    }

    @Test
    fun `given list of contacts, when insert contacts is called, then it should be inserted into the db`() = runTest {
        val mockedContactDomain = List(5) {
            ContactDomain(
                id = it.toLong(),
                name = "Test $it",
                phoneNumber = "Phone $it",
                dateUpdated = 123456789L
            )
        }
        sut.insert(mockedContactDomain)
        val expectedSize = mockedContactDomain.size
        val actualSize = sut.get().first().size
        Assert.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun `given list of contacts, when insert contacts is called and an item exists and data is different, then it should be updated in the db`() = runTest {
        val mockedId = 12L
        val mockedContactDomain = ContactDomain(
            id = mockedId,
            name = "Test",
            phoneNumber = "Phone"
        )

        val updatedMockedContactDomain = ContactDomain(
            id = mockedId,
            name = "Test-456",
            phoneNumber = "Phone"
        )

        sut.insert(listOf(mockedContactDomain))

        val initialDateUpdated = sut.get(id = mockedId).dateUpdated

        sut.insert(listOf(updatedMockedContactDomain))

        val actualDateUpdated = sut.get(id = mockedId).dateUpdated

        Assert.assertNotEquals(initialDateUpdated, actualDateUpdated)
    }

    @Test
    fun `given list of contacts, when insert contacts is called and an item exists and data is same, then it should not be updated in the db`() = runTest {
        val mockedId = 12L
        val mockedContactDomain = ContactDomain(
            id = mockedId,
            name = "Test",
            phoneNumber = "Phone"
        )

        sut.insert(listOf(mockedContactDomain))

        val initialDateUpdated = sut.get(id = mockedId).dateUpdated

        sut.insert(listOf(mockedContactDomain))

        val actualDateUpdated = sut.get(id = mockedId).dateUpdated

        Assert.assertEquals(initialDateUpdated, actualDateUpdated)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
