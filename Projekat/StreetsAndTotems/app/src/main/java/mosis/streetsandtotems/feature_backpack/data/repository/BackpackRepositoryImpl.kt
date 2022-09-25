package mosis.streetsandtotems.feature_backpack.data.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.feature_backpack.data.data_source.FirebaseBackpackDataSource
import mosis.streetsandtotems.feature_backpack.domain.repository.BackpackRepository
import mosis.streetsandtotems.feature_map.domain.model.ResourceType

class BackpackRepositoryImpl(
    private val preferenceDataSource: PreferencesDataStore,
    private val firebaseBackpackDataSource: FirebaseBackpackDataSource,
) : BackpackRepository {
    override suspend fun dropResource(l: GeoPoint, itemCount: Int, type: ResourceType) {
        firebaseBackpackDataSource.dropResource(l, itemCount, type)
    }

    override suspend fun placeTotem(l: GeoPoint) {
        firebaseBackpackDataSource.placeTotem(
            preferenceDataSource.getUserId(),
            l,
            preferenceDataSource.getUserSquadId()
        )
    }
}