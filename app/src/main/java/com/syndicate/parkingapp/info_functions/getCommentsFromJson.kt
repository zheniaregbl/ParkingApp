package com.syndicate.parkingapp.info_functions

import android.util.Log
import com.syndicate.parkingapp.data.model.CommentObject
import com.syndicate.parkingapp.data.model.CommentState
import com.syndicate.parkingapp.data.model.ParkingItem
import org.json.JSONObject

fun getCommentsFromJson(
    jsonObject: JSONObject,
    parkingItem: ParkingItem
): CommentState {

    val listComments = ArrayList<CommentObject>()

    val jsonArray = jsonObject.getJSONArray("feedback")

    for (i in 0..<jsonArray.length()) {

        val item = jsonArray.getJSONObject(i)

        listComments.add(
            CommentObject(
                id = item.getInt("id"),
                comment = item.getString("comment"),
                rating = item.getInt("rating"),
                name = item.getString("name")
            )
        )
    }

    Log.d("getCommentsFromJson", jsonObject.getBoolean("hasowncomment").toString())

    return CommentState(
        hasOwnComment = jsonObject.getBoolean("hasowncomment"),
        parkingItem = parkingItem,
        listComments = listComments
    )
}
