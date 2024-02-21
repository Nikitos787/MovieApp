package com.example.movieapp.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.presentation.navigation.Screen
import com.example.movieapp.presentation.screen.common.ListContent

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val topRateMovies = homeScreenViewModel.moviesState.collectAsLazyPagingItems()

    ListContent(items = topRateMovies) { id ->
        Log.d("ListScreen", id)
        navHostController.navigate(Screen.Details.passImageId(id))
    }
}