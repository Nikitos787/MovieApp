package com.example.movieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.example.movieapp.presentation.screen.detail.MovieDetailScreen
import com.example.movieapp.presentation.screen.home.HomeScreen
import com.example.movieapp.util.Constants

@ExperimentalPagingApi
@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Screen.Details.route,
            arguments = listOf(
                navArgument(Constants.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetailScreen(navHostController = navHostController)
        }
    }
}
