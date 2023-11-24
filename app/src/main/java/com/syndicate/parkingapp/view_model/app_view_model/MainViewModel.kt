package com.syndicate.parkingapp.view_model.app_view_model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.syndicate.parkingapp.data.model.BottomSheetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
): ViewModel() {

    val state = MutableStateFlow(BottomSheetInfo())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OpenBottomSheet -> {
                Log.d("viewModelClick", "click")

                state.update { it.copy(
                    isOpen = true,
                    point = event.point
                ) }
            }
        }
    }
}