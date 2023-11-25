package com.syndicate.parkingapp

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.syndicate.parkingapp.core.AppConstants
import com.syndicate.parkingapp.data.model.ParkingItem
import com.syndicate.parkingapp.data.model.PlaceMarkType
import com.syndicate.parkingapp.navigation.AppNavGraph
import com.syndicate.parkingapp.ui.screens.map_screen.components.ClusterView
import com.syndicate.parkingapp.ui.theme.ParkingAppTheme
import com.syndicate.parkingapp.ui.utils.LockScreenOrientation
import com.syndicate.parkingapp.view_model.app_view_model.MainEvent
import com.syndicate.parkingapp.view_model.app_view_model.MainViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mapView: MapView
    private lateinit var collection: MapObjectCollection
    private lateinit var clusterCollection: ClusterizedPlacemarkCollection

    private var placeMarkTapListener = MapObjectTapListener { _, point ->
        Log.d("checkClickMark", "click: $point")
        true
    }

    private var clusterListener = ClusterListener { cluster ->
        val placeMarkTypes = cluster.placemarks.map {
            val parkingItem = it.userData as ParkingItem
            val remainingPlace = parkingItem.ordinary + parkingItem.handicapped

            when {
                remainingPlace < parkingItem.total / 4 -> PlaceMarkType.RED

                remainingPlace > parkingItem.total / 2 -> PlaceMarkType.GREEN

                remainingPlace < parkingItem.total / 2 -> PlaceMarkType.YELLOW

                else -> PlaceMarkType.YELLOW
            }
        }

        cluster.appearance.setView(
            ViewProvider(
                ClusterView(this).apply {
                    setData(placeMarkTypes)
                }
            )
        )
        cluster.appearance.zIndex = 100f
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        fun drawBitmap(
            text: String,
            type: PlaceMarkType
        ): Bitmap {
            val source = BitmapFactory.decodeResource(
                this.resources,
                when (type) {
                    PlaceMarkType.RED -> R.drawable.red_mark
                    PlaceMarkType.YELLOW -> R.drawable.yellow_mark
                    PlaceMarkType.GREEN -> R.drawable.green_mark
                }
            )
            val bitmap = source.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(bitmap)

            val paint = Paint()
            paint.color = Color.White.toArgb()
            paint.textSize = 60f
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(text, canvas.width / 2f, canvas.height / 1.35f, paint)

            return bitmap
        }

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        MapKitFactory.setApiKey(AppConstants.API_KEY)
        MapKitFactory.initialize(this)

        mapView = MapView(this)

        mapView.mapWindow.map.logo.setAlignment(
            Alignment(
                HorizontalAlignment.RIGHT,
                VerticalAlignment.TOP
            )
        )

        setContent {

            val scope = rememberCoroutineScope()
            var dateState by remember {
                mutableStateOf(Date())
            }
            val scaffoldState = rememberBottomSheetScaffoldState()

            val viewModel = hiltViewModel<MainViewModel>()

            val state by viewModel.state.collectAsState()
            val appState by viewModel.appState.collectAsState()
            val accountState by viewModel.accountState.collectAsState()
            val commentState by viewModel.commentState.collectAsState()
            val listParkingPlaces = viewModel.listParkingPlaces.observeAsState()

            collection = mapView.mapWindow.map.mapObjects.addCollection()

            clusterCollection = collection.addClusterizedPlacemarkCollection(clusterListener)

            placeMarkTapListener = MapObjectTapListener { mapObject, _ ->
                val item = mapObject.userData as ParkingItem
                viewModel.onEvent(MainEvent.GetInfoParkingPlace(item))
                viewModel.onEvent(MainEvent.GetCommentsByParking(item))

                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }

                true
            }

            LaunchedEffect(key1 = dateState) {
                viewModel.onEvent(MainEvent.GetParkingPlacesMark)
                viewModel.onEvent(MainEvent.GetAccountInfo)

                Log.d("updateMinutes", "update")

                mapView.mapWindow.map.mapObjects.clear()

                delay(1000 * 60)

                dateState = Date()
            }

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(MainEvent.GetParkingPlacesMark)
                viewModel.onEvent(MainEvent.GetAccountInfo)
            }

            if (listParkingPlaces.value != null) {

                listParkingPlaces.value!!.forEach { parkingItem ->

                    val remainingPlace = parkingItem.ordinary + parkingItem.handicapped

                    clusterCollection.addPlacemark().apply {
                        geometry = Point(parkingItem.latitude, parkingItem.longitude)
                        setIcon(
                            ImageProvider.fromBitmap(
                                drawBitmap(
                                    remainingPlace.toString(),
                                    when {
                                        remainingPlace > parkingItem.total / 2 -> PlaceMarkType.GREEN

                                        remainingPlace < parkingItem.total / 4 -> PlaceMarkType.RED

                                        remainingPlace < parkingItem.total / 2 -> PlaceMarkType.YELLOW

                                        else -> PlaceMarkType.YELLOW
                                    }
                                )
                            )
                        )
                        userData = parkingItem
                        this.addTapListener(placeMarkTapListener)
                    }
                }
            }

            clusterCollection.clusterPlacemarks(60.0, 18)

            ParkingAppTheme {
                LockScreenOrientation(
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                )

                AppNavGraph(
                    mapView = mapView,
                    scaffoldState = scaffoldState,
                    viewModel = viewModel,
                    markerTabListener = placeMarkTapListener,
                    state = state,
                    appState = appState,
                    accountObject = accountState,
                    commentState = commentState
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