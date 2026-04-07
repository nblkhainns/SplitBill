package com.nabilakhairunnisa0028.asesmen1.navigation

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}