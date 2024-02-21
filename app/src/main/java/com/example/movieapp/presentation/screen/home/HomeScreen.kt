package com.example.movieapp.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.presentation.navigation.Screen
import com.example.movieapp.presentation.screen.common.MovieListContent

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val topRateMovies = homeScreenViewModel.moviesState.collectAsLazyPagingItems()

    MovieListContent(items = topRateMovies) { id ->
        navHostController.navigate(Screen.Details.passImageId(id))
    }
}
