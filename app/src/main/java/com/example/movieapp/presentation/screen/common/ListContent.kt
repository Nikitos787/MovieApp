package com.example.movieapp.presentation.screen.common

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.model.topRatedMovieList.MovieListEntity
import com.example.movieapp.util.Constants.BASE_URL_FOR_IMAGE

@Composable
fun ListContent(
    items: LazyPagingItems<MovieListEntity>,
    onClick: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_header_of_app),
                contentDescription = "header",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = items.itemCount) { index ->
                    val topRatedMovie = items[index]
                    topRatedMovie?.let {
                        Log.d("ListContent", "in lazy colum with id: ${topRatedMovie.id}")
                        TopRatedMovieItem(
                            movieListEntity = it,
                            click = {
                                Log.d("DetailsScreen", "click")
                                onClick(it.id) })

                    }
                }
                items.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = items.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = items.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TopRatedMovieItem(
    movieListEntity: MovieListEntity,
    click: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${BASE_URL_FOR_IMAGE}${movieListEntity.backdrop_path}")
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_placeholder),
        error = painterResource(id = R.drawable.ic_placeholder)
    )
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f, label = ""
    )
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(200.dp)
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clickable { click() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painter,
            contentDescription = "movie poster",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .alpha(transition),
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(0.5f),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movieListEntity.title,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
            RatingCounter(
                modifier = Modifier.weight(3f),
                votes = "${(movieListEntity.vote_average * 10).toInt()}%"
            )
        }

    }
}

@Composable
fun RatingCounter(modifier: Modifier, votes: String) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Star, contentDescription = "rating icon", tint = Color.Red)
        Text(
            text = votes,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Preview
@Composable
fun TopRatedMovieItemPrev() {
    TopRatedMovieItem(
        movieListEntity = MovieListEntity(
            "1",
            true,
            "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            "en",
            "Somthing",
            "fdfdf",
            10.1,
            "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            "2020-01-01",
            "Some title",
            true,
            12.2,
            1
        )
    ) {}
}

@Composable
fun RetrySection(error: String, onRetry: () -> Unit) {
    Column {
        Text(
            text = error,
            color = Color.Red,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}