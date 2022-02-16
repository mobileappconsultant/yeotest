package com.android.yeophonebook.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.yeophonebook.data.dao.ContactDao
import com.android.yeophonebook.data.entity.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDataBase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
