package com.example.movieapp.model.list

data class MovieListResponse(
    val page: Int,
    val results: List<MovieListEntity>,
    val total_pages: Int,
    val total_results: Int
)
