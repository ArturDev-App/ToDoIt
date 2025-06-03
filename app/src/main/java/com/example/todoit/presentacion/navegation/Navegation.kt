package com.example.todoit.presentacion.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoit.AuthManager
import com.example.todoit.presentacion.screens.home.HomeScreen
import com.example.todoit.presentacion.screens.login.LoginScreen


@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHostController(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }
    val startDestination = if (authManager.isUserSignedIn()) {
        ScreenNavegation.Home.name
    } else {
        ScreenNavegation.LoginScreen.name
    }


    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }
    ) {
        composable(route = ScreenNavegation.LoginScreen.name) {

            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(ScreenNavegation.Home.name) {
                        popUpTo(ScreenNavegation.LoginScreen.name) { inclusive = true }
                    }
                }
            )
        }

        composable(route = ScreenNavegation.Home.name) {
            HomeScreen()
        }
    }
}
