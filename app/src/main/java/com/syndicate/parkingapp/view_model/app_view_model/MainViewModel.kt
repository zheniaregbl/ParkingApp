package com.syndicate.parkingapp.view_model.app_view_model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.parkingapp.data.model.AccountObject
import com.syndicate.parkingapp.data.model.AppState
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import com.syndicate.parkingapp.data.model.BottomSheetInfoType
import com.syndicate.parkingapp.data.model.CommentState
import com.syndicate.parkingapp.data.model.ParkingItem
import com.syndicate.parkingapp.domain.repository.ParkingRepository
import com.syndicate.parkingapp.info_functions.getAccountInfoFromJson
import com.syndicate.parkingapp.info_functions.getCommentsFromJson
import com.syndicate.parkingapp.info_functions.getParkingFromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: ParkingRepository
): ViewModel() {

    val state = MutableStateFlow(BottomSheetInfo())
    val appState = MutableStateFlow(AppState())
    val accountState = MutableStateFlow(AccountObject())
    val commentState = MutableStateFlow(CommentState())

    private var _listParkingPlaces = MutableLiveData<List<ParkingItem>>()
    var listParkingPlaces: LiveData<List<ParkingItem>> = _listParkingPlaces

    init {
        appState.update { it.copy(
            isRegistered = sharedPreferences.getBoolean("is_registered", false)
        ) }

        getParkingPlaces()
        getAccountInfo()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetInfoParkingPlace -> {
                state.update { it.copy(
                    bottomSheetInfoType = BottomSheetInfoType.PARKING_PLACE,
                    parkingItem = event.parkingItem
                ) }
            }

            is MainEvent.GetInfoCurrentBooking -> {
                state.update { it.copy(
                    bottomSheetInfoType = BottomSheetInfoType.CURRENT_BOOKING,
                    bookingObject = event.bookingObject
                ) }
            }

            MainEvent.GetParkingPlacesMark -> {
                getParkingPlaces()
            }

            MainEvent.GetAccountInfo -> {
                getAccountInfo()
            }

            is MainEvent.GetCommentsByParking -> {
                getComments(event.parkingItem)
            }

            is MainEvent.SendComment -> {
                sendComment(event.message, event.rating, event.parkingId)
            }

            is MainEvent.RemoveComment -> {
                removeComment(event.parkingId)
            }
        }
    }

    private fun getParkingPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonArray = repository.getParkingPlaces()

            _listParkingPlaces.postValue(
                getParkingFromJson(jsonArray)
            )
        }
    }

    private fun getAccountInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = sharedPreferences.getString("token", "")

            if (token != null && token != "") {
                val jsonObject = repository.getAccountInfo(token)

                accountState.value = getAccountInfoFromJson(jsonObject)
            }
        }
    }

    private fun getComments(parkingItem: ParkingItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = sharedPreferences.getString("token", "")!!
            val json = repository.getComments(token, parkingItem.id)

            commentState.value = getCommentsFromJson(json, parkingItem)
        }
    }

    private fun sendComment(message: String, rating: Int, parkingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = sharedPreferences.getString("token", "")!!
            repository.sendComment(
                token,
                parkingId.toString(),
                message,
                rating.toString()
            )
        }
    }

    private fun removeComment(parkingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = sharedPreferences.getString("token", "")!!
            val status = repository.removeComment(
                token,
                parkingId.toString()
            )

            Log.d("removeComment", status.toString())
        }
    }
}