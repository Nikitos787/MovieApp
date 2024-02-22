package com.example.movieapp.model.list

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.util.Constants.TOP_RATED_MOVIES_TABLE

@Entity(tableName = TOP_RATED_MOVIES_TABLE)
data class MovieListEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
