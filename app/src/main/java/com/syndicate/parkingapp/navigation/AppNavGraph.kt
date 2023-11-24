package com.syndicate.parkingapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView

@Composable
fun AppNavGraph(
    mapView: MapView,
    markerTabListener: MapObjectTapListener,
    state: BottomSheetInfo
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
                .padding(paddingValues)
        ) {
            AppNavigation(
                navController = navController,
                mapView = mapView,
                paddingValues = paddingValues,
                markerTabListener = markerTabListener,
                state = state
            )
        }
    }
}