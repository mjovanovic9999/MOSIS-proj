package mosis.streetsandtotems.feature_map.presentation.components.search_results

import androidx.compose.runtime.Composable

interface SearchResultItem {
    @Composable
    fun getLazyColumnItem()
}