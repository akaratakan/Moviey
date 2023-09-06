package com.aa.data_usecase.detail

import com.aa.data.IoDispatcher
import com.aa.data.MovieRepository
import com.aa.data_usecase.base.ActionFlowUseCase
import com.aa.local.entities.movie.MovieDetailEntity
import com.aa.model.generic.Magic
import com.aa.model.movie.MovieDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by atakanakar on 25.08.2023
 */
class DetailDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ActionFlowUseCase<MovieDetailEntity>() {

    private lateinit var detailEntity: MovieDetailEntity
    private var isActive = false

    fun init(
        isFavAction: Boolean,
        detailResponse: MovieDetailResponse? = null,
        entity: MovieDetailEntity? = null
    ) {
        detailEntity =
            if (detailResponse != null) { movieRepository.convertToEntity(detailResponse) } else entity!!
        this.isActive = isFavAction
    }

    override fun performAction(): Flow<Magic<MovieDetailEntity>> = flow {
        if (isActive) {
            detailEntity.isFav = true
            movieRepository.insertDetail(detailEntity = detailEntity)
            emit(Magic.success(detailEntity))
            Timber.d("Insert movie detail succeed")
        } else {
            movieRepository.deleteDetail(detailEntity = detailEntity)
            emit(Magic.success(detailEntity))
            Timber.d("Delete movie detail succeed")
        }
    }.catch {
        Timber.e(it, "Db Action movie detail error")
        emit(Magic.failure(it.message.toString()))
    }.flowOn(ioDispatcher)
}