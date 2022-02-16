package com.android.yeophonebook.di

import com.android.yeophonebook.data.dao.ContactDao
import com.android.yeophonebook.domain.ContactMapper
import com.android.yeophonebook.domain.repository.ContactRepository
import com.android.yeophonebook.domain.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideContactRepository(
        contactDao: ContactDao,
        contactMapper: ContactMapper
    ): ContactRepository = ContactRepositoryImpl(contactDao, contactMapper)
}