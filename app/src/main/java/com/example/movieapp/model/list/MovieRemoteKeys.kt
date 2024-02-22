package com.example.movieapp.model.list

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.util.Constants.TOP_RATED_MOVIES_REMOTE_KEYS_TABLE

@Entity(tableName = TOP_RATED_MOVIES_REMOTE_KEYS_TABLE)
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)

