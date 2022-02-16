package com.android.yeophonebook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.yeophonebook.data.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContactsToDb(contactEntities: List<ContactEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContactToDb(contactEntity: ContactEntity)


    @Query("SELECT * FROM contacts")
    fun getContactsFromDb(): Flow<List<ContactEntity>>
}
