package com.aa.data_usecase

import com.aa.data.IoDispatcher
import com.aa.data.MovieRepository
import com.aa.data_usecase.base.ActionFlowUseCase
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
class FetchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ActionFlowUseCase<SearchResponse>() {

    private lateinit var name: String

    fun initName(name:String) {
        this.name = name
    }

    override fun performAction(): Flow<Magic<SearchResponse>> = flow {
        emit(Magic.loading())
        val detail = movieRepository.fetchMovie(name)
        emit(Magic.success(detail))
    }.catch {
        Timber.e(it, "Fetch movie detail error")
        emit(Magic.failure(it.message.toString()))
    }.flowOn(ioDispatcher)
}