package com.syndicate.parkingapp.ui.screens.map_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.syndicate.parkingapp.data.model.ParkingItem
import com.syndicate.parkingapp.ui.theme.GreenButton
import com.syndicate.parkingapp.ui.theme.OrangeComment

@Composable
fun ParkingMenuInfo(
    modifier: Modifier = Modifier,
    parkingItem: ParkingItem = ParkingItem(),
    navigateToComment: () -> Unit = { }
) {
    val emptyStar = 5 - parkingItem.rating

    AnimatedContent(
        targetState = parkingItem,
        label = ""
    ) { parkingItem ->
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.svg_parking),
                        contentDescription = null
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                )

                Column {
                    Text(
                        text = parkingItem.address,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color.Black.copy(0.8f)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(parkingItem.rating) {
                            Box {
                                Image(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_star_fill),
                                    contentDescription = null
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .width(6.dp)
                            )
                        }

                        repeat(emptyStar) {
                            Box {
                                Image(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_star_empty),
                                    contentDescription = null
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .width(6.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .background(
                                    color = OrangeComment
                                )
                                .padding(6.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { navigateToComment() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Просмотр отзывов",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Количество мест",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                    Text(
                        text = "${parkingItem.ordinary} мест",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Количество инвалидных мест",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                    Text(
                        text = "${parkingItem.handicapped} мест",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Имеется ли видеонаблюдение",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                    Text(
                        text = if (parkingItem.hasCamera) "Да" else "Нет",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (parkingItem.hasCamera) GreenButton else Color.Black.copy(0.8f)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewParkingMenuInfo() {
    ParkingMenuInfo()
}