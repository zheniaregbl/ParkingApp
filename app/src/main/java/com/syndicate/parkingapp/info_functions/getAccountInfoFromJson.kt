package com.syndicate.parkingapp.info_functions

import com.syndicate.parkingapp.data.model.AccountObject
import com.syndicate.parkingapp.data.model.BookingObject
import com.syndicate.parkingapp.data.model.CarObject
import org.json.JSONObject

fun getAccountInfoFromJson(
    jsonObject: JSONObject
): AccountObject {

    val listBooking = ArrayList<BookingObject>()
    val listCars = ArrayList<CarObject>()

    val jsonCars = jsonObject.getJSONArray("cars")
    val reservations = jsonObject.getJSONArray("reservations")
    val occupied = jsonObject.getJSONArray("occupied")

    for (i in 0..<jsonCars.length()) {

        val car = jsonCars.getJSONObject(i)

        listCars.add(
            CarObject(
                id = car.getInt("id"),
                serialNumber = car.getString("carplateNumber"),
                name = car.getString("name"),
                visible = car.getBoolean("visible")
            )
        )
    }

    for (i in 0..<reservations.length()) {

        val item = reservations.getJSONObject(i)

        listBooking.add(
            BookingObject(
                id = item.getInt("id"),
                parkingPlaceId = item.getInt("parkingPlaceId"),
                carId = item.getInt("carId"),
                startDate = item.getString("startDate"),
                lastDate = "",
                carName = item.getString("name"),
                carNumber = item.getString("carNumber"),
                address = item.getString("address"),
                remainingTime = item.getInt("remainingTime"),
                rateName = "Бронь",
                booking = true
            )
        )
    }

    for (i in 0..<occupied.length()) {

        val item = occupied.getJSONObject(i)

        listBooking.add(
            BookingObject(
                id = item.getInt("id"),
                parkingPlaceId = item.getInt("parkingPlaceId"),
                carId = item.getInt("carId"),
                startDate = item.getString("startDate"),
                lastDate = item.getString("lastDate"),
                carName = item.getString("name"),
                carNumber = item.getString("carNumber"),
                address = item.getString("address"),
                remainingTime = item.getInt("remainingTime"),
                passTime = item.getInt("passTime"),
                rateName = item.getString("rateName"),
                price = item.getInt("price"),
                booking = false
            )
        )
    }

    return AccountObject(
        balance = jsonObject.getInt("balance"),
        listCars = listCars,
        listBooking = listBooking
    )
}