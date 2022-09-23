package mosis.streetsandtotems.feature_main.domain.use_case

import mosis.streetsandtotems.feature_main.domain.repository.MainRepository

class LeaveSquad(private val mainRepository: MainRepository) {
    suspend operator fun invoke() = mainRepository.leaveSquad()
}