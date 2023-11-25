package com.syndicate.parkingapp.data.model

data class BottomSheetInfo(
    val bottomSheetInfoType: BottomSheetInfoType = BottomSheetInfoType.PARKING_PLACE,
    val parkingItem: ParkingItem = ParkingItem(),
    val bookingObject: BookingObject = BookingObject()
)
