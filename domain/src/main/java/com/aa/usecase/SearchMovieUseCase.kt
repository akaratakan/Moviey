package com.aa.usecase

import com.aa.data.IoDispatcher
import com.aa.data.MovieRepository
import com.aa.usecase.base.ActionFlowUseCase
import com.aa.model.generic.Magic
import com.aa.model.search.SearchResponse
import com.squareup.moshi.JsonDataException
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
class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ActionFlowUseCase<SearchResponse>() {

    private lateinit var name: String

    fun initName(name:String) {
        this.name = name
    }

    override fun performAction(): Flow<Magic<SearchResponse>> = flow {
        emit(Magic.loading())
        try {
            val detail = movieRepository.searchMovie(name)
            emit(Magic.success(detail))
        } catch (e: JsonDataException) {
            emit(Magic.failure("Movie not found"))
        }

    }.catch {
        Timber.e(it, "Search movie error")
        emit(Magic.failure(it.message.toString()))
    }.flowOn(ioDispatcher)
}