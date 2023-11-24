package com.syndicate.parkingapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.syndicate.parkingapp.data.model.EnterType
import com.syndicate.parkingapp.ui.screens.change_password_screen.ChangePasswordScreen
import com.syndicate.parkingapp.ui.screens.map_screen.MapScreen
import com.syndicate.parkingapp.ui.screens.registration_screen.RegistrationScreen
import com.syndicate.parkingapp.ui.screens.splash_screen.SplashScreen
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView

@Composable
fun AppNavigation(
    navController: NavHostController,
    mapView: MapView,
    paddingValues: PaddingValues,
    markerTabListener: MapObjectTapListener,
    state: BottomSheetInfo
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SplashScreen.route
    ) {
        composable(
            route = ScreenRoute.SplashScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            SplashScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navigateToMap = {
                    navController.navigate(ScreenRoute.MapScreen.route) {
                        popUpTo(0)
                    }
                },
                navigateToRegistration = {
                    navController.navigate(ScreenRoute.AuthorizationScreen.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(
            route = ScreenRoute.AuthorizationScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            RegistrationScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                enterType = EnterType.AUTHORIZATION,
                navigateToMap = {
                    navController.navigate(ScreenRoute.MapScreen.route) {
                        popUpTo(0)
                    }
                },
                navigateToChangePassword = {
                    navController.navigate(ScreenRoute.ChangePasswordScreen.route)
                }
            )
        }

        composable(
            route = ScreenRoute.ChangePasswordScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            ChangePasswordScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navigateToRegistration = {
                    navController.navigate(ScreenRoute.AuthorizationScreen.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(
            route = ScreenRoute.MapScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            MapScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                mapView = mapView,
                markerTabListener = markerTabListener,
                state = state
            )
        }
    }
}