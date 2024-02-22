package com.example.movieapp.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.presentation.screen.common.ErrorMessage
import com.example.movieapp.presentation.screen.common.MovieDetailItemData
import com.example.movieapp.presentation.screen.common.PageLoader
import com.example.movieapp.presentation.screen.common.TopAppBarrForMovieDetailScreen
import com.example.movieapp.util.Constants.ID
import com.example.movieapp.util.Resource
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@Composable
fun MovieDetailScreen(
    navHostController: NavHostController,
    detailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movieDetail = detailViewModel.moviesState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        val id = navHostController.currentBackStackEntry?.arguments?.getString(ID)
        id?.let {
            detailViewModel.setId(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBarrForMovieDetailScreen(
                movieDetail = movieDetail.value,
                navHostController = navHostController
            )
        }
    )
    { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            MovieDetailItemDataSection(movieDetailsResource = movieDetail.value) {
                coroutineScope.launch {
                    detailViewModel.getMovieDetail()
                }
            }
        }
    }
}

@Composable
fun MovieDetailItemDataSection(
    movieDetailsResource: Resource<MovieDetailsEntity>,
    onRetry: () -> Unit
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(state),
        contentAlignment = Alignment.Center
    ) {
        when (movieDetailsResource) {
            is Resource.Error -> {
                ErrorMessage(movieDetailsResource.message!!) {
                    onRetry()
                }
            }
            is Resource.Loading -> {
                PageLoader()
            }
            is Resource.Success -> {
                MovieDetailItemData(movieDetailsResource = movieDetailsResource)
            }
        }
    }
}
