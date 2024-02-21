package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.topRatedMovieList.MovieListEntity
import com.example.movieapp.util.Constants

@Dao
interface TopRatedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMovies(movies: List<MovieListEntity>)

    @Query("DELETE FROM ${Constants.TOP_RATED_MOVIES_TABLE}")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM ${Constants.TOP_RATED_MOVIES_TABLE}")
    fun getAllMovies(): PagingSource<Int, MovieListEntity>
}
