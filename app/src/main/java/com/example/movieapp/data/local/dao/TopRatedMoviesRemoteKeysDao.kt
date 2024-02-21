package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.topRatedMovieList.MovieRemoteKeys
import com.example.movieapp.util.Constants

@Dao
interface TopRatedMoviesRemoteKeysDao {

    @Query("SELECT * FROM ${Constants.TOP_RATED_MOVIES_REMOTE_KEYS_TABLE} WHERE id =:id")
    suspend fun getRemoteKeys(id: String): MovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeys>)

    @Query("DELETE FROM ${Constants.TOP_RATED_MOVIES_REMOTE_KEYS_TABLE}")
    suspend fun deleteAllRemoteKeys()
}
