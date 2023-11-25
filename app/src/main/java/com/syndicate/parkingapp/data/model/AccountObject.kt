package com.syndicate.parkingapp.data.model

class AccountObject(
    val balance: Int = 0,
    val listCars: List<CarObject> = emptyList(),
    val listBooking: List<BookingObject> = emptyList()
)