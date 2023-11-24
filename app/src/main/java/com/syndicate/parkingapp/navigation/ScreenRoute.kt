package com.syndicate.parkingapp.navigation

sealed class ScreenRoute(val route: String) {

    data object SplashScreen: ScreenRoute("splash_screen")
    data object AuthorizationScreen: ScreenRoute("auth_screen")
    data object ChangePasswordScreen: ScreenRoute("change_password_screen")
    data object MapScreen: ScreenRoute("map_screen")
}