package com.example.movieapp.presentation.navigation

import com.example.movieapp.util.Constants.DETAIL_SCREEN_PATH
import com.example.movieapp.util.Constants.HOME_SCREEN_PATH

sealed class Screen(val route: String){
    data object Home: Screen(HOME_SCREEN_PATH)
    data object Details: Screen("${DETAIL_SCREEN_PATH}/{id}") {
        fun passImageId(id: String) : String {
            return "${DETAIL_SCREEN_PATH}/$id"
        }
    }
}
