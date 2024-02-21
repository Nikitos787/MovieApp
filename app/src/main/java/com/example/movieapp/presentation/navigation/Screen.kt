package com.example.movieapp.presentation.navigation

sealed class Screen(val route: String){
    data object Home: Screen("home_screen")
    data object Details: Screen("details_screen/{id}") {
        fun passImageId(id: String) : String {
            return "details_screen/$id"
        }
    }
}
