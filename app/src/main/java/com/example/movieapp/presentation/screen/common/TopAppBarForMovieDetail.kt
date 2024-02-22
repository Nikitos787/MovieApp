package com.example.movieapp.presentation.screen.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieapp.R
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.ui.theme.BlueHeader
import com.example.movieapp.util.Constants
import com.example.movieapp.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarrForMovieDetailScreen(
    movieDetail: Resource<MovieDetailsEntity>,
    navHostController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(
                    R.string.more_about_movie,
                    movieDetail.data?.title ?: Constants.EMPTY
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.icon_for_back),
                    modifier = Modifier
                        .size(36.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BlueHeader,
            navigationIconContentColor = Color.White
        )
    )
}
