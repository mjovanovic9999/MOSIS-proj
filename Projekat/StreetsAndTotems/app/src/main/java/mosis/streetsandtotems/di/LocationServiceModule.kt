package mosis.streetsandtotems.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository
import mosis.streetsandtotems.core.domain.repository.UserOnlineStatusRepository
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseServiceDataSource
import mosis.streetsandtotems.feature_map.data.repository.MapServiceRepositoryImpl
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
import mosis.streetsandtotems.services.LocationServiceInventoryEvents
import mosis.streetsandtotems.services.LocationServiceMainScreenEvents
import mosis.streetsandtotems.services.LocationServiceMapScreenEvents
import mosis.streetsandtotems.services.use_case.*

@Module
@InstallIn(ServiceComponent::class)
object LocationServiceModule {
    @Provides
    @ServiceScoped
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @ServiceScoped
    fun provideMapDataSource(db: FirebaseFirestore): FirebaseServiceDataSource =
        FirebaseServiceDataSource(db)

    @Provides
    @ServiceScoped
    fun provideMapRepository(
        firebaseServiceDataSource: FirebaseServiceDataSource,
        auth: FirebaseAuth,
        preferencesDataStore: PreferencesDataStore
    ): MapServiceRepository =
        MapServiceRepositoryImpl(firebaseServiceDataSource, auth, preferencesDataStore)

    @Provides
    @ServiceScoped
    fun provideLocationServiceUseCases(
        mapServiceRepository: MapServiceRepository,
        locationServiceMapScreenEventsFlow: MutableSharedFlow<LocationServiceMapScreenEvents>,
        userOnlineStatusRepository: UserOnlineStatusRepository,
        preferenceRepository: PreferenceRepository,
        locationServiceMainScreenEventsFlow: MutableSharedFlow<LocationServiceMainScreenEvents>,
        locationServiceInventoryEventsFlow: MutableSharedFlow<LocationServiceInventoryEvents>,
    ): LocationServiceUseCases = LocationServiceUseCases(
        UpdatePlayerLocation(mapServiceRepository),
        RegisterCallbacks(
            mapServiceRepository,
            locationServiceMapScreenEventsFlow,
            locationServiceMainScreenEventsFlow,
            locationServiceInventoryEventsFlow
        ),
        RemoveCallbacks(mapServiceRepository),
        ChangeUserOnlineStatus(userOnlineStatusRepository, preferenceRepository),
        RegisterCallbackOnSquadInvite(
            mapServiceRepository, locationServiceMapScreenEventsFlow
        ),
        RegisterCallbackOnKickVote(
            preferenceRepository, mapServiceRepository, locationServiceMapScreenEventsFlow
        ),
        RemoveCallbackOnKickVote(mapServiceRepository),
        RemoveCallbackOnSquadInvite(mapServiceRepository)
    )
}