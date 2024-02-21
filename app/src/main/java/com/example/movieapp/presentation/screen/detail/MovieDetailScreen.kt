package com.example.movieapp.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.presentation.screen.common.ErrorMessage
import com.example.movieapp.presentation.screen.common.PageLoader
import com.example.movieapp.util.Constants
import com.example.movieapp.util.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalPagingApi
@Composable
fun MovieDetailScreen(
    navHostController: NavHostController,
    detailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movieDetail = detailViewModel.moviesState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        val id = navHostController.currentBackStackEntry?.arguments?.getString("id")
        id?.let {
            detailViewModel.setId(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "More about movie: ${movieDetail.value.data?.title ?: ""}",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "icon for back",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan)
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
            MovieDetailItemData(movieDetailsResource = movieDetail.value) {
                coroutineScope.launch {
                    detailViewModel.getMovieDetail()
                }
            }
        }
    }
}

@Composable
fun MovieDetailItemData(
    movieDetailsResource: Resource<MovieDetailsEntity>,
    onRetry: () -> Unit
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(state)
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
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = movieDetailsResource.data?.title ?: "Default title",
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(18.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Expand row icon",
                        tint = Color(0xffffa500)
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp)
                            .align(Alignment.CenterVertically),
                        text = movieDetailsResource.data?.vote_average.toString(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 12.sp
                    )
                }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.BASE_URL_FOR_IMAGE}${movieDetailsResource.data?.poster_path}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    error = painterResource(id = R.drawable.ic_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "Description",
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp),
                    text = movieDetailsResource.data?.overview ?: "Default overview",
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }

}
