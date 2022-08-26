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
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.data.repository.MapRepositoryImpl
import mosis.streetsandtotems.feature_map.domain.repository.MapRepository

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
    fun provideMapDataSource(db: FirebaseFirestore): FirebaseMapDataSource =
        FirebaseMapDataSource(db)

    @Provides
    @ServiceScoped
    fun provideMapRepository(
        firebaseMapDataSource: FirebaseMapDataSource,
        auth: FirebaseAuth
    ): MapRepository = MapRepositoryImpl(firebaseMapDataSource, auth)
}