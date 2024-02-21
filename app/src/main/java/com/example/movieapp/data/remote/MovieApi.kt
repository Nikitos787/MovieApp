package com.example.movieapp.data.remote

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.model.topRatedMovieList.MovieListResponse
import com.example.movieapp.util.Constants.AUTHORIZATION_BEARER_HEADER
import com.example.movieapp.util.Constants.ID
import com.example.movieapp.util.Constants.ID_PATH
import com.example.movieapp.util.Constants.MOVIE_TOP_RATED_PATH
import com.example.movieapp.util.Constants.PAGE_QUERY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @Headers("$AUTHORIZATION_BEARER_HEADER ${BuildConfig.API_KEY}")
    @GET(MOVIE_TOP_RATED_PATH)
    suspend fun getTopRatedMovies(@Query(PAGE_QUERY) page: Int): MovieListResponse

    @Headers("$AUTHORIZATION_BEARER_HEADER ${BuildConfig.API_KEY}")
    @GET(ID_PATH)
    suspend fun getMovieDetails(@Path(ID) id: String): MovieDetailsEntity
}
