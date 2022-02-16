package com.android.yeophonebook.domain.usecases

import com.android.yeophonebook.domain.model.ContactDomain
import com.android.yeophonebook.domain.repository.ContactRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveContactsToDbUseCase @Inject constructor(private val contactRepository: ContactRepository) :
    FlowUseCase<Unit, List<ContactDomain>>() {
    override suspend fun buildFlowUseCase(params: List<ContactDomain>?): Flow<Unit> {
        return flow { params?.let { contactRepository.insert(it) } }
    }
}