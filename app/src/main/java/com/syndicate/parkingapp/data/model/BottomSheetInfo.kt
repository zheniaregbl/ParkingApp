package com.syndicate.parkingapp.data.model

import com.yandex.mapkit.geometry.Point

data class BottomSheetInfo(
    val isOpen: Boolean = false,
    val point: Point = Point(58.534416, 31.241163)
)
