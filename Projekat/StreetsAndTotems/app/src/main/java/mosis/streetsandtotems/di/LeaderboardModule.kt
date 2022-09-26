package mosis.streetsandtotems.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.feature_leaderboards.data.data_source.LeaderboardDataSource
import mosis.streetsandtotems.feature_leaderboards.data.repository.LeaderboardRepositoryImpl
import mosis.streetsandtotems.feature_leaderboards.domain.repository.LeaderboardRepository
import mosis.streetsandtotems.feature_leaderboards.domain.use_case.GetUserData
import mosis.streetsandtotems.feature_leaderboards.domain.use_case.LeaderboardUseCases
import mosis.streetsandtotems.feature_leaderboards.domain.use_case.RegisterLeaderboardCallback
import mosis.streetsandtotems.feature_leaderboards.domain.use_case.RemoveLeaderboardCallback
import mosis.streetsandtotems.feature_leaderboards.presentation.LeaderboardScreenEvents

@Module
@InstallIn(ViewModelComponent::class)
object LeaderboardModule {
    @Provides
    @ViewModelScoped
    fun provideLocationServiceLeaderboardEventsMutableFlow(): MutableSharedFlow<LeaderboardScreenEvents> =
        MutableSharedFlow()

    @Provides
    @ViewModelScoped
    fun provideLocationServiceLeaderboardEventsFlow(locationServiceInventoryEventsMutableFlow: MutableSharedFlow<LeaderboardScreenEvents>): SharedFlowWrapper<LeaderboardScreenEvents> =
        SharedFlowWrapper(locationServiceInventoryEventsMutableFlow)

    @Provides
    @ViewModelScoped
    fun provideLeaderboardDataSource(db: FirebaseFirestore): LeaderboardDataSource =
        LeaderboardDataSource(db)

    @Provides
    @ViewModelScoped
    fun provideLeaderboardRepository(
        auth: FirebaseAuth, leaderboardDataSource: LeaderboardDataSource
    ): LeaderboardRepository = LeaderboardRepositoryImpl(auth, leaderboardDataSource)

    @Provides
    @ViewModelScoped
    fun provideLeaderboardUseCases(
        leaderboardRepository: LeaderboardRepository,
        leaderboardEventsFlow: MutableSharedFlow<LeaderboardScreenEvents>
    ): LeaderboardUseCases = LeaderboardUseCases(
        RegisterLeaderboardCallback(leaderboardRepository, leaderboardEventsFlow),
        RemoveLeaderboardCallback(leaderboardRepository),
        GetUserData(leaderboardRepository)
    )
}