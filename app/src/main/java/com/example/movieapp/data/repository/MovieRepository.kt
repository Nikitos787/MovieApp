package com.example.movieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.local.paging.MoviesMediator
import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.model.topRatedMovieList.MovieListEntity
import com.example.movieapp.util.Constants.ITEMS_PER_PAGE
import com.example.movieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val database: MovieDatabase
) {

    fun getAllMovies(): Flow<PagingData<MovieListEntity>> {
        val pagingSourceFactory = {
            database.getTopRatedMoviesDao().getAllMovies()
        }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = MoviesMediator(
                movieApi = movieApi,
                movieDatabase = database
            )
        ).flow
    }

    fun getMovieDetails(id: String): Flow<Resource<MovieDetailsEntity>> = flow {
        emit(Resource.Loading())
        try {
            val response = movieApi.getMovieDetails(id)
            emit(Resource.Success(data = response))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        }
    }
}
