package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.TopRatedMoviesDao
import com.example.movieapp.data.local.dao.TopRatedMoviesRemoteKeysDao
import com.example.movieapp.model.topRatedMovieList.MovieListEntity
import com.example.movieapp.model.topRatedMovieList.MovieRemoteKeys

@Database(
    entities = [MovieListEntity::class, MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getTopRatedMoviesDao(): TopRatedMoviesDao

    abstract fun getTopRatedMoviesRemoteKeysDao() : TopRatedMoviesRemoteKeysDao
}
