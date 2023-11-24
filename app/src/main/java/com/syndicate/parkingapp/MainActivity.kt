package com.syndicate.parkingapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.syndicate.parkingapp.core.AppConstants
import com.syndicate.parkingapp.navigation.AppNavGraph
import com.syndicate.parkingapp.ui.theme.ParkingAppTheme
import com.syndicate.parkingapp.ui.utils.LockScreenOrientation
import com.syndicate.parkingapp.view_model.app_view_model.MainEvent
import com.syndicate.parkingapp.view_model.app_view_model.MainViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mapView: MapView

    private var placeMarkTapListener = MapObjectTapListener { _, point ->
        Log.d("checkClickMark", "click: $point")
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        MapKitFactory.setApiKey(AppConstants.API_KEY)
        MapKitFactory.initialize(this)

        mapView = MapView(this)

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val state by viewModel.state.collectAsState()

            placeMarkTapListener = MapObjectTapListener { _, point ->
                viewModel.onEvent(MainEvent.OpenBottomSheet(point))
                true
            }

            ParkingAppTheme {
                LockScreenOrientation(
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                )

                AppNavGraph(
                    mapView = mapView,
                    markerTabListener = placeMarkTapListener,
                    state = state
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}