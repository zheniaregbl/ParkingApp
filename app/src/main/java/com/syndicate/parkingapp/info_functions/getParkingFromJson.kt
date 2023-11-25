package com.syndicate.parkingapp.info_functions

import com.syndicate.parkingapp.data.model.ParkingItem
import com.syndicate.parkingapp.data.model.RateItem
import org.json.JSONArray
import org.json.JSONObject

fun getParkingFromJson(
    jsonArray: JSONArray
): List<ParkingItem> {

    if (jsonArray.toString() == "")
        return emptyList()

    val listParkingPlace = ArrayList<ParkingItem>()

    for (i in 0..<jsonArray.length()) {

        val jsonObject = jsonArray.getJSONObject(i)

        val listRates = ArrayList<RateItem>()

        val id = jsonObject.getInt("id")
        val address = jsonObject.getString("address")
        val latitude = jsonObject.getJSONObject("coordinates").getDouble("latitude")
        val longitude = jsonObject.getJSONObject("coordinates").getDouble("longitude")
        val hasCamera = jsonObject.getBoolean("has_camera")
        val handicapped = jsonObject.getJSONObject("places").getInt("handicapped")
        val ordinary = jsonObject.getJSONObject("places").getInt("ordinary")
        val total = jsonObject.getJSONObject("places").getInt("total")
        val rating = jsonObject.getInt("rating")

        val jsonRates = jsonObject.getJSONArray("rates")

        for (i in 0..<jsonRates.length()) {

            val rateJson = jsonRates.getJSONObject(i)

            listRates.add(
                RateItem(
                    id = rateJson.getInt("id"),
                    name = rateJson.getString("name"),
                    startPrice = rateJson.getInt("startPrice"),
                    freeTime = rateJson.getInt("freeTime"),
                    priceMinute = rateJson.getInt("priceMinute")
                )
            )
        }

        listParkingPlace.add(
            ParkingItem(
                id = id,
                address = address,
                latitude = latitude,
                longitude = longitude,
                hasCamera = hasCamera,
                handicapped = handicapped,
                ordinary = ordinary,
                total = total,
                rating = rating,
                listRates = listRates
            )
        )
    }

    return listParkingPlace
}