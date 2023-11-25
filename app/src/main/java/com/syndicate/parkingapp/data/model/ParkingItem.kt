package com.syndicate.parkingapp.data.model

class ParkingItem(
    val id: Int = 1,
    val address: String = "Ул. Свободы, Триндулиар 7-к1",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val hasCamera: Boolean = false,
    val handicapped: Int = 0,
    val ordinary: Int = 0,
    val total: Int = 0,
    val rating: Int = 0,
    val listRates: List<RateItem> = emptyList()
)
