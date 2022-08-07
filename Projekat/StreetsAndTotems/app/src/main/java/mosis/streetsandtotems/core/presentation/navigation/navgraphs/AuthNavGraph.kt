package mosis.streetsandtotems.core.presentation.navigation.navgraphs

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class AuthNavGraph(
    val start: Boolean = false
)
