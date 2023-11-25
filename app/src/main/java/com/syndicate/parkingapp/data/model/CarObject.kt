package com.syndicate.parkingapp.data.model

data class CarObject(
    val id: Int = 0,
    val serialNumber: String = "-",
    val name: String = "-",
    val visible: Boolean = false
)