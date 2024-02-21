package com.example.movieapp.data.remote

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.model.topRatedMovieList.MovieListResponse
import com.example.movieapp.util.Constants.AUTHORIZATION_BEARER_HEADER
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @Headers("$AUTHORIZATION_BEARER_HEADER ${BuildConfig.API_KEY}")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MovieListResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): MovieDetailsEntity
}
