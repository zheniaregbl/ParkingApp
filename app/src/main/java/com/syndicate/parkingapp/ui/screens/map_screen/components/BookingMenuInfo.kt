package com.syndicate.parkingapp.ui.screens.map_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.syndicate.parkingapp.data.model.BookingObject
import com.syndicate.parkingapp.ui.theme.BookingListItemGray
import com.syndicate.parkingapp.ui.theme.CancelColor
import com.syndicate.parkingapp.ui.theme.CommentNameColor
import com.syndicate.parkingapp.ui.theme.GreenButton

@Composable
fun BookingMenuInfo(
    modifier: Modifier = Modifier,
    bookingObject: BookingObject = BookingObject(
        startDate = "2023-11-26T00:23:14.314101",
        carNumber = "K742CM53",
        carName = "Daewoo Nexia",
        price = 100,
        remainingTime = 10,
        address = "Ул. Свободы, Триндулиар 7-к1",
        rateName = "1 час",
        booking = false
    )
) {

    val dateString = bookingObject.startDate.substring(0, 10).replace('-', '.')
    val timeString = bookingObject.startDate.substring(11, 16)

    val passHours = bookingObject.passTime / 60
    val passMinutes = bookingObject.passTime % 60

    val remainingHours = if (bookingObject.remainingTime > 0)
        bookingObject.remainingTime / 60 else 0

    val remainingMinutes = if (bookingObject.remainingTime > 0)
        bookingObject.remainingTime % 60 else 0

    AnimatedContent(
        targetState = bookingObject,
        label = ""
    ) { bookingObject ->
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
                        text = bookingObject.address,
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
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(7.dp))
                                .background(
                                    color = CommentNameColor
                                )
                                .padding(5.dp)
                        ) {
                            Text(
                                text = "$dateString $timeString",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(7.dp))
                                .background(
                                    color = CommentNameColor
                                )
                                .padding(5.dp)
                        ) {
                            Text(
                                text = bookingObject.rateName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = BookingListItemGray
                    )
                    .padding(
                        vertical = 18.dp
                    )
                    .padding(
                        horizontal = 16.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bookingObject.carName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = Color.White
                        )
                        .padding(
                            5.dp
                        )
                ) {
                    Text(
                        text = bookingObject.carNumber,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AnimatedVisibility(visible = true) {
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
                                text = "Прошло времени",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black.copy(0.8f)
                            )
                            Text(
                                text = if (passHours > 0) "$passHours ч. $passMinutes мин."
                                    else "$passMinutes мин.",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black.copy(0.8f)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(6.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Осталось времени",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                    Text(
                        text = if (remainingHours > 0) "$remainingHours ч. $remainingMinutes мин."
                            else "$remainingMinutes мин.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(0.8f)
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                        .padding(
                            end = 10.dp
                        )
                        .clip(
                            RoundedCornerShape(5.dp)
                        )
                        .background(
                            color = CancelColor
                        )
                        .padding(
                            vertical = 20.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Отменить",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(5.dp)
                        )
                        .background(
                            color = GreenButton
                        )
                        .padding(
                            vertical = 20.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Занять",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.White
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
fun PreviewBookingMenuInfo() {
    BookingMenuInfo()
}