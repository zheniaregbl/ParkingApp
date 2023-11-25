package com.syndicate.parkingapp.view_model.app_view_model

import com.syndicate.parkingapp.data.model.BookingObject
import com.syndicate.parkingapp.data.model.ParkingItem

sealed interface MainEvent {

    data class GetInfoParkingPlace(
        val parkingItem: ParkingItem
    ): MainEvent

    data class GetInfoCurrentBooking(
        val bookingObject: BookingObject
    ): MainEvent

    data object GetParkingPlacesMark: MainEvent

    data object GetAccountInfo: MainEvent

    data class GetCommentsByParking(
        val parkingItem: ParkingItem
    ): MainEvent

    data class SendComment(
        val message: String,
        val rating: Int,
        val parkingId: Int
    ): MainEvent

    data class RemoveComment(
        val parkingId: Int
    ): MainEvent
}
