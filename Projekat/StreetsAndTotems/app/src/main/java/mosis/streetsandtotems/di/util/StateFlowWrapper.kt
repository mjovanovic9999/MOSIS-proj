package mosis.streetsandtotems.di.util

import kotlinx.coroutines.flow.StateFlow

data class StateFlowWrapper<T>(val flow: StateFlow<T>)