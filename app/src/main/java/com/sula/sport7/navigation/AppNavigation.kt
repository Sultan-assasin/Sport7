package com.sula.sport7.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sula.sport7.FakeScreenViewModel
import com.sula.sport7.screens.MainScreen.MainScreen
import com.sula.sport7.screens.SplashScreen.SplashScreen
import com.sula.sport7.screens.StartPage.StartScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: FakeScreenViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.name) {
        composable(Screens.MainScreen.name + "/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            MainScreen(navController, link = id)
        }
        composable(Screens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(Screens.StartScreen.name) {
            StartScreen(navController = navController)
        }
    }
}