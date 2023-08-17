package com.aa.data_usecase.base

import com.aa.model.generic.Magic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class FlowUseCase<T> {

    private val _trigger = MutableStateFlow(true)

    /**
     * Exposes result of this use case
     */
    val resultFlow: Flow<Magic<T>> = _trigger.flatMapLatest {
        makeMagic()
    }
    /**
     * Triggers the execution of this use case
     */
    suspend fun launch() {
        _trigger.emit(!(_trigger.value))
    }

    protected abstract fun makeMagic() : Flow<Magic<T>>
}