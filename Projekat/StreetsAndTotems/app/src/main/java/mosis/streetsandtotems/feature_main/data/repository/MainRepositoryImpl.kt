package mosis.streetsandtotems.feature_main.data.repository

import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.feature_main.domain.repository.MainRepository
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource

class MainRepositoryImpl(
    private val firebaseMapDataSource: FirebaseMapDataSource,
    private val preferenceDataSource: PreferencesDataStore
) : MainRepository {
    override suspend fun leaveSquad() {
        firebaseMapDataSource.leaveSquad(
            preferenceDataSource.getUserId(), preferenceDataSource.getUserSquadId()
        )
    }
}