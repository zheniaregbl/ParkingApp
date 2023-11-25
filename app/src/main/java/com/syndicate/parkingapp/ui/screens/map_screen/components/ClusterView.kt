package com.syndicate.parkingapp.ui.screens.map_screen.components

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.data.model.PlaceMarkType

class ClusterView(context: Context) : LinearLayout(context) {

    private val greenText by lazy { findViewById<TextView>(R.id.text_green_pins) }
    private val yellowText by lazy { findViewById<TextView>(R.id.text_yello_pins) }
    private val redText by lazy { findViewById<TextView>(R.id.text_red_pins) }

    private val greenLayout by lazy { findViewById<View>(R.id.layout_green_group) }
    private val yellowLayout by lazy { findViewById<View>(R.id.layout_yellow_group) }
    private val redLayout by lazy { findViewById<View>(R.id.layout_red_group) }

    init {
        inflate(context, R.layout.cluster_view, this)
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL
        setBackgroundResource(R.drawable.cluster_back)
    }

    fun setData(placemarkTypes: List<PlaceMarkType>) {
        PlaceMarkType.values().forEach {
            updateViews(placemarkTypes, it)
        }
    }

    private fun updateViews(
        placemarkTypes: List<PlaceMarkType>,
        type: PlaceMarkType
    ) {
        val (textView, layoutView) = when (type) {
            PlaceMarkType.GREEN -> greenText to greenLayout
            PlaceMarkType.YELLOW -> yellowText to yellowLayout
            PlaceMarkType.RED -> redText to redLayout
        }

        val value = placemarkTypes.countTypes(type)

        Log.d("checkValueInfo", "$value $type")

        textView.text = value.toString()
        layoutView.isVisible = value != 0
    }

    private fun List<PlaceMarkType>.countTypes(type: PlaceMarkType) = count { it == type }
}