package com.aa.data_usecase.base

import com.aa.data.IoDispatcher
import com.aa.data.MovieRepository
import com.aa.data_usecase.base.ActionFlowUseCase
import com.aa.local.entities.localsearch.LocalSearch
import com.aa.model.generic.Magic
import com.aa.model.search.SearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by atakanakar on 16.08.2023
 */
class SearchHistoryUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit>() {

    private lateinit var name: String

    fun initName(name:String) {
        this.name = name
    }

    override fun makeMagic(): Flow<Magic<Unit>> = flow {
        emit(Magic.loading())
//        movieRepository.getSearchedText()
        emit(Magic.success(Unit))
    }.catch {
        Timber.e(it, "Fetch movie detail error")
        emit(Magic.failure(it.message.toString()))
    }.flowOn(ioDispatcher)

}