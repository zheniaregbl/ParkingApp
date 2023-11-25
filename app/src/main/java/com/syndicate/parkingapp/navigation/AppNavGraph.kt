package com.syndicate.parkingapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.syndicate.parkingapp.data.model.AccountObject
import com.syndicate.parkingapp.data.model.AppState
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.syndicate.parkingapp.data.model.CommentState
import com.syndicate.parkingapp.data.model.ParkingItem
import com.syndicate.parkingapp.view_model.app_view_model.MainViewModel
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    mapView: MapView,
    scaffoldState: BottomSheetScaffoldState,
    viewModel: MainViewModel,
    markerTabListener: MapObjectTapListener,
    state: BottomSheetInfo,
    appState: AppState,
    accountObject: AccountObject,
    commentState: CommentState
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppNavigation(
                navController = navController,
                mapView = mapView,
                scaffoldState = scaffoldState,
                viewModel = viewModel,
                paddingValues = paddingValues,
                markerTabListener = markerTabListener,
                state = state,
                appState = appState,
                accountObject = accountObject,
                commentState = commentState
            )
        }
    }
}