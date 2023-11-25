package com.syndicate.parkingapp.ui.screens.map_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.ui.theme.GreenButton

@Composable
fun TopBar(
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    onClickBurger: () -> Unit = { },
    onClickToBalance: () -> Unit = { },
    balance: Int = 0
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    color = Color.White
                )
                .height(36.dp)
                .padding(
                    horizontal = 7.dp
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickBurger()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.svg_burger),
                contentDescription = null
            )
        }

        Spacer(
            modifier = Modifier
                .width(10.dp)
        )

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    color = Color.White
                )
                .height(36.dp)
                .padding(
                    horizontal = 16.dp
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickToBalance()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$balance ₽",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = GreenButton
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewTopBar() {
    TopBar()
}