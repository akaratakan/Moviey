package com.aa.usecase

import com.aa.data.IoDispatcher
import com.aa.data.MovieRepository
import com.aa.model.generic.Magic
import com.aa.model.movie.MovieDetailResponse
import com.aa.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by atakanakar on 23.08.2023
 */
class FetchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<MovieDetailResponse>() {

    private lateinit var imdbID: String
    fun initName(name: String) {
        this.imdbID = name
    }

    override fun makeMagic(): Flow<Magic<MovieDetailResponse>> = flow {
        emit(Magic.loading())
        val entity = movieRepository.fetchDetailWithId(imdbID)
        if (entity == null) {
            val detail = movieRepository.fetchMovieWithId(imdbID)
            emit(Magic.success(detail))
        } else {
            val detail = movieRepository.convertToResponse(entity)
            detail.isFav = true
            emit(Magic.success(detail))
        }
    }.catch {
        Timber.e(it, "Fetch movie detail error")
        emit(Magic.failure(it.message.toString()))
    }.flowOn(ioDispatcher)
}