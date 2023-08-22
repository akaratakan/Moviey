package com.aa.data

import com.aa.local.entities.localsearch.LocalSearch
import com.aa.local.entities.localsearch.SearchDao
import com.aa.model.search.SearchResponse
import com.aa.network.api.MovieApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by atakanakar
 */

class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val searchDao: SearchDao
) {

    suspend fun fetchMovie(name: String): SearchResponse {
        return api.searchMovie(name)
    }

    fun insertSearch(localSearch: LocalSearch) {
        searchDao.insertLocalSearch(localSearch)
    }

    fun getSearchedText() = flow{
        emit(searchDao.getAllSearchedText())
    }
}