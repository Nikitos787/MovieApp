package com.example.movieapp.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.topRatedMovieList.MovieListEntity
import com.example.movieapp.model.topRatedMovieList.MovieRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class MoviesMediator(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, MovieListEntity>() {

    private val topRatedMoviesDao = movieDatabase.getTopRatedMoviesDao()
    private val topRatedMoviesRemoteKeysDao = movieDatabase.getTopRatedMoviesRemoteKeysDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieListEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = movieApi.getTopRatedMovies(page = currentPage).results
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    topRatedMoviesDao.deleteAllMovies()
                    topRatedMoviesRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { movieListEntity ->
                    MovieRemoteKeys(
                        id = movieListEntity.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                topRatedMoviesDao.addAllMovies(response)
                topRatedMoviesRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, MovieListEntity>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                topRatedMoviesRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeysForFirstItem(
        state: PagingState<Int, MovieListEntity>
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            topRatedMoviesRemoteKeysDao.getRemoteKeys(id = movie.id)
        }
    }

    private suspend fun getRemoteKeysForLastItem(
        state: PagingState<Int, MovieListEntity>
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            topRatedMoviesRemoteKeysDao.getRemoteKeys(id = movie.id)
        }
    }
}
