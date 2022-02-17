package com.android.yeophonebook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.yeophonebook.data.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContactToDb(contactEntity: ContactEntity) : Long

    @Query("SELECT * FROM contacts")
    fun getAll(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContactById(id: Long): ContactEntity

    @Update
    fun update(contactEntity: ContactEntity)
}
