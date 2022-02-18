package com.android.yeophonebook.domain.usecases

import com.android.yeophonebook.domain.model.ContactDomain
import com.android.yeophonebook.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SaveContactsToDbUseCase @Inject constructor(private val contactRepository: ContactRepository) :
    FlowUseCase<Unit, List<ContactDomain>>() {
    override suspend fun buildFlowUseCase(params: List<ContactDomain>?): Flow<Unit> {
        return flowOf(contactRepository.insert(params ?: emptyList()))
    }
}
