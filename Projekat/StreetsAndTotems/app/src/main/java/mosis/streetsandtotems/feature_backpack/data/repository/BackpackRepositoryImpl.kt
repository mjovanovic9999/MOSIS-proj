package mosis.streetsandtotems.feature_backpack.data.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.feature_backpack.data.data_source.FirebaseBackpackDataSource
import mosis.streetsandtotems.feature_backpack.domain.repository.BackpackRepository
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

class BackpackRepositoryImpl(
    private val preferenceDataSource: PreferencesDataStore,
    private val firebaseBackpackDataSource: FirebaseBackpackDataSource,
) : BackpackRepository {
    override suspend fun dropItem(
        l: GeoPoint,
        itemCount: Int,
        type: IconType.ResourceType?,
        oldInventory: UserInventoryData,
    ) {
        firebaseBackpackRepository.dropItem(
            l,
            itemCount,
            type,
            oldInventory,
            preferenceDataSource.getUserId(),
            preferenceDataSource.getUserSquadId(),
        )
    }
}