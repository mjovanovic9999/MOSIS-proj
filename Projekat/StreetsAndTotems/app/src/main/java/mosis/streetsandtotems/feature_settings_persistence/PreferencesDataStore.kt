//package mosis.streetsandtotems.feature_settings_persistence
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//object PreferencesDataStore {
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//
//
//    fun readSth() {
//        val EXAMPLE_COUNTER = booleanPreferencesKey("example_counter")
//        val exampleCounterFlow: Flow<Int> = this.Context.dataStore.data
//            .map { preferences ->
//                // No type safety.
//                preferences[EXAMPLE_COUNTER] ?: 0
//            }
//
//    }
//
////    suspend fun incrementCounter() {
////        Context.dataStore.edit { settings ->
////            val currentCounterValue = settings["example_counter"] ?: 0
////            settings["example_counter"] = currentCounterValue + 1
////        }
////    }
//
//
//}