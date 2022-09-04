package mosis.streetsandtotems.di.util

import kotlinx.coroutines.flow.SharedFlow

data class SharedFlowWrapper<T>(val flow: SharedFlow<T>)