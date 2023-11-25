package com.syndicate.parkingapp.ui.screens.comment_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.syndicate.parkingapp.R

@Composable
fun StarButton(
    onClick: () -> Unit = { },
    active: Boolean = false
) {
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(
                id = if (active) R.drawable.svg_star_fill else R.drawable.svg_star_empty
            ),
            contentDescription = null
        )
    }
}