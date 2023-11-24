package com.syndicate.parkingapp.ui.screens.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.syndicate.parkingapp.view_model.app_view_model.MainViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapView: MapView,
    markerTabListener: MapObjectTapListener,
    state: BottomSheetInfo
) {
    val viewModel = hiltViewModel<MainViewModel>()

    val context = LocalContext.current

    val scaffoldState = rememberBottomSheetScaffoldState()

    val listPoints = listOf(
        Point(58.534416, 31.241163),
        Point(58.53155, 31.236659),
        Point(58.530387, 31.235834),
        Point(58.530145, 31.253569),
        Point(58.530568, 31.254967),
        Point(58.522908, 31.246954),
        Point(58.537764, 31.222762),
        Point(58.537243, 31.222658),
        Point(58.532437, 31.259638),
        Point(58.529224, 31.258003),
        Point(58.521809, 31.247475),
        Point(58.532188, 31.267074),
        Point(58.526456, 31.261447),
        Point(58.515348, 31.249197),
        Point(58.525807, 31.258932),
        Point(58.520967, 31.269496),
        Point(58.526239, 31.271056),
        Point(58.549332, 31.231341),
        Point(58.523562, 31.256938),
        Point(58.53302, 31.267274),
        Point(58.521079, 31.264652),
        Point(58.527957, 31.276186),
        Point(58.546859, 31.211823),
        Point(58.546026, 31.259144),
        Point(58.548829, 31.231009),
        Point(58.520874, 31.227838),
        Point(58.527343, 31.272136),
        Point(58.51952, 31.258115),
        Point(58.547472, 31.218388),
        Point(58.516266, 31.2749),
        Point(58.515292, 31.248714),
        Point(58.549017, 31.268594),
        Point(58.516727, 31.268998),
        Point(58.516567, 31.274336),
        Point(58.512371, 31.238609),
        Point(58.522697, 31.285267),
        Point(58.51483, 31.267505),
        Point(58.509977, 31.226279),
        Point(58.523515, 31.287694),
        Point(58.517815, 31.287126),
        Point(58.512888, 31.269637),
        Point(58.541422, 31.290309)
    )

    val imageProvider = ImageProvider.fromResource(context, R.drawable.placemark_icon)

    LaunchedEffect(key1 = state) {
        if (state.isOpen) scaffoldState.bottomSheetState.expand()
    }

    LaunchedEffect(key1 = Unit) {
        listPoints.forEach { point ->
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = point
                setIcon(imageProvider)
                this.addTapListener(markerTabListener)
            }
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column {
                Text(text = "${state.point.latitude}")
                Text(text = "${state.point.longitude}")
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = {
                    mapView
                },
                update = {

                }
            )
        }
    }
}