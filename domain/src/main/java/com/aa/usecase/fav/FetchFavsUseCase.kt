package com.aa.usecase.fav

import com.aa.data.IoDispatcher
import com.aa.data.MovieRepository
import com.aa.usecase.base.FlowUseCase
import com.aa.local.entities.movie.MovieDetailEntity
import com.aa.model.generic.Magic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject


class FetchFavsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<List<MovieDetailEntity>>() {

    override fun makeMagic(): Flow<Magic<List<MovieDetailEntity>>> = flow {
        emit(Magic.loading())
        val entities = movieRepository.fetchAll() ?: emptyList()
        emit(Magic.success(entities))
    }.catch {
        Timber.e(it, "Fetch movies detail error")
        emit(Magic.failure(it.message.toString()))
    }.flowOn(ioDispatcher)

}