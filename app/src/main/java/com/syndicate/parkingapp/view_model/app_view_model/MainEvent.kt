package com.syndicate.parkingapp.view_model.app_view_model

import com.yandex.mapkit.geometry.Point

sealed interface MainEvent {

    data class OpenBottomSheet(
        val point: Point
    ): MainEvent
}