package com.syndicate.parkingapp.data.model

data class RateItem(
    val id: Int = 0,
    val name: String = "",
    val startPrice: Int = 5,
    val freeTime: Int = 0,
    val priceMinute: Int = 1
)