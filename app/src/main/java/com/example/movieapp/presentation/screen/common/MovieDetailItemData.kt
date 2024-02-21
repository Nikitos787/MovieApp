package com.example.movieapp.presentation.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.util.Constants
import com.example.movieapp.util.Resource

@Composable
fun MovieDetailItemData(movieDetailsResource: Resource<MovieDetailsEntity>) {
    Column {
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = movieDetailsResource.data?.title
                ?: stringResource(R.string.default_title),
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
                contentDescription = stringResource(R.string.expand_row_icon),
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
            text = stringResource(R.string.more_info),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.released_date),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "${movieDetailsResource.data?.release_date}", color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.duration),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "${movieDetailsResource.data?.runtime} minutes",
                color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.revenue),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "${movieDetailsResource.data?.revenue}$", color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.budget),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "${movieDetailsResource.data?.budget}$", color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }

        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(R.string.description),
            color = Color.DarkGray,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp),
            text = movieDetailsResource.data?.overview
                ?: stringResource(R.string.default_overview),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
