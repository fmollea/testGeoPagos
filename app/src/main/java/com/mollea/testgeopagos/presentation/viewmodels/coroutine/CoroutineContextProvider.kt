package com.mollea.testgeopagos.presentation.viewmodels.coroutine

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Suppress("PropertyName")
open class CoroutineContextProvider @Inject constructor() {
    open val Main: CoroutineContext by lazy {   Dispatchers.Main }
    open val IO: CoroutineContext by lazy {   Dispatchers.IO }
}