package com.nabilakhairunnisa0028.asesmen1.navigation

import android.R
import android.health.connect.datatypes.ExerciseRoute

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
}