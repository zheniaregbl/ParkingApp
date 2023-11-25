package com.syndicate.parkingapp.data.model

data class BookingObject(
    val id: Int = 0,
    val parkingPlaceId: Int = 0,
    val carId: Int = 0,
    val startDate: String = "",
    val lastDate: String = "",
    val carNumber: String = "-",
    val carName: String = "-",
    val price: Int = 0,
    val remainingTime: Int = 0,
    val passTime: Int = 0,
    val address: String = "-",
    val rateName: String = "-",
    val booking: Boolean = false
)
