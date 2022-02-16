package com.android.yeophonebook.di

import android.content.Context
import androidx.room.Room
import com.android.yeophonebook.data.dao.ContactDao
import com.android.yeophonebook.data.db.ContactDataBase
import com.android.yeophonebook.utils.AppConstant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ContactDataBase {
        return Room.databaseBuilder(
            context,
            ContactDataBase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactDao(contactDataBase: ContactDataBase): ContactDao {
        return contactDataBase.contactDao()
    }
}
