package com.syndicate.parkingapp.ui.screens.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.syndicate.parkingapp.data.model.AccountObject
import com.syndicate.parkingapp.data.model.BookingObject
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.syndicate.parkingapp.data.model.BottomSheetInfoType
import com.syndicate.parkingapp.ui.screens.map_screen.components.BookingList
import com.syndicate.parkingapp.ui.screens.map_screen.components.BookingMenuInfo
import com.syndicate.parkingapp.ui.screens.map_screen.components.ParkingMenuInfo
import com.syndicate.parkingapp.ui.screens.map_screen.components.TopBar
import com.yandex.mapkit.mapview.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapView: MapView,
    scaffoldState: BottomSheetScaffoldState,
    state: BottomSheetInfo,
    accountObject: AccountObject,
    getAccountInfo: () -> Unit = { },
    navigateToComment: () -> Unit = { },
    bookingTapListener: (BookingObject) -> Unit = { }
) {

    LaunchedEffect(key1 = Unit) {
        getAccountInfo()
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetContent = {
            when (state.bottomSheetInfoType) {

                BottomSheetInfoType.PARKING_PLACE -> {
                    ParkingMenuInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 15.dp,
                                end = 15.dp,
                                bottom = 20.dp
                            )
                            .navigationBarsPadding(),
                        parkingItem = state.parkingItem,
                        navigateToComment = {
                            navigateToComment()
                            mapView.mapWindow.map.mapObjects.clear()
                        }
                    )
                }

                BottomSheetInfoType.CURRENT_BOOKING -> {
                    BookingMenuInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 15.dp,
                                end = 15.dp,
                                bottom = 20.dp
                            )
                            .navigationBarsPadding(),
                        bookingObject = state.bookingObject
                    )
                }

                else -> {
                    BookingMenuInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 15.dp,
                                end = 15.dp,
                                bottom = 20.dp
                            )
                            .navigationBarsPadding()
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = {
                    mapView
                }
            )

            BookingList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 25.dp
                    )
                    .align(Alignment.BottomStart),
                listBooking = accountObject.listBooking,
                bookingTapListener = bookingTapListener
            )

            TopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 15.dp,
                        top = 25.dp
                    ),
                balance = accountObject.balance
            )
        }
    }
}