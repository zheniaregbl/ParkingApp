package com.syndicate.parkingapp.data.model

class CommentState(
    val hasOwnComment: Boolean = false,
    val parkingItem: ParkingItem = ParkingItem(),
    val listComments: List<CommentObject> = emptyList()
)