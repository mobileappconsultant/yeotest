package com.android.yeophonebook.domain.usecases

import com.android.yeophonebook.domain.model.ContactDomain
import com.android.yeophonebook.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchContactsFromDbUseCase @Inject constructor(private val contactRepository: ContactRepository) :
    FlowUseCase<List<ContactDomain>, Unit>() {
    override suspend fun buildFlowUseCase(params: Unit?): Flow<List<ContactDomain>> =
        contactRepository.get()
}
