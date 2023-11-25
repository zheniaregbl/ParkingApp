package com.syndicate.parkingapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.syndicate.parkingapp.data.model.AccountObject
import com.syndicate.parkingapp.data.model.AppState
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.syndicate.parkingapp.data.model.CommentState
import com.syndicate.parkingapp.data.model.EnterType
import com.syndicate.parkingapp.ui.screens.change_password_screen.ChangePasswordScreen
import com.syndicate.parkingapp.ui.screens.comment_screen.CommentScreen
import com.syndicate.parkingapp.ui.screens.map_screen.MapScreen
import com.syndicate.parkingapp.ui.screens.registration_screen.RegistrationScreen
import com.syndicate.parkingapp.ui.screens.splash_screen.SplashScreen
import com.syndicate.parkingapp.view_model.app_view_model.MainEvent
import com.syndicate.parkingapp.view_model.app_view_model.MainViewModel
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    mapView: MapView,
    scaffoldState: BottomSheetScaffoldState,
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    markerTabListener: MapObjectTapListener,
    state: BottomSheetInfo,
    appState: AppState,
    accountObject: AccountObject,
    commentState: CommentState
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
                    .fillMaxSize(),
                isRegistered = appState.isRegistered,
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
                scaffoldState = scaffoldState,
                state = state,
                accountObject = accountObject,
                getAccountInfo = {
                    viewModel.onEvent(MainEvent.GetAccountInfo)
                },
                navigateToComment = {
                    navController.navigate(ScreenRoute.CommentScreen.route)
                },
                bookingTapListener = { bookingObject ->
                    scope.launch {
                        viewModel.onEvent(MainEvent.GetInfoCurrentBooking(bookingObject))
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }

        composable(
            route = ScreenRoute.CommentScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            CommentScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 15.dp
                    )
                    .padding(paddingValues),
                commentState = commentState,
                navigateToBack = {
                    navController.navigate(ScreenRoute.MapScreen.route) {
                        popUpTo(0)
                    }
                },
                getParkingPlaces = {
                    viewModel.onEvent(MainEvent.GetParkingPlacesMark)
                },
                updateComment = {
                    viewModel.onEvent(MainEvent.GetCommentsByParking(commentState.parkingItem))
                },
                sendComment = { message, rating ->
                    viewModel.onEvent(
                        MainEvent.SendComment(
                            message,
                            rating,
                            commentState.parkingItem.id
                        )
                    )
                    viewModel.onEvent(MainEvent.GetCommentsByParking(commentState.parkingItem))
                },
                removeComment = {
                    viewModel.onEvent(MainEvent.RemoveComment(commentState.parkingItem.id))
                    viewModel.onEvent(MainEvent.GetCommentsByParking(commentState.parkingItem))
                }
            )
        }
    }
}