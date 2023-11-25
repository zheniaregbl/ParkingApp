package com.syndicate.parkingapp.ui.screens.map_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.parkingapp.data.model.BookingObject
import com.syndicate.parkingapp.ui.theme.BookingListItemGray

@Composable
fun BookingList(
    modifier: Modifier = Modifier.fillMaxWidth(),
    listBooking: List<BookingObject> = listOf(
        BookingObject(), BookingObject(),
        BookingObject(), BookingObject(),
        BookingObject(), BookingObject()
    ),
    bookingTapListener: (BookingObject) -> Unit = { }
) {
    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier
                .padding(
                    start = 15.dp
                ),
            text = "Активные",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.Black.copy(alpha = 0.8f)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .width(15.dp)
                )
            }

            listBooking.forEachIndexed { index, bookingItem ->

                item {
                    BookingListItem(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                bookingTapListener(bookingItem)
                            },
                        item = bookingItem
                    )
                    if (index != listBooking.lastIndex)
                        Spacer(
                            modifier = Modifier
                                .width(20.dp)
                        )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .width(15.dp)
                )
            }
        }
    }

}

@Composable
fun BookingListItem(
    modifier: Modifier = Modifier,
    item: BookingObject = BookingObject()
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.White
                )
                .padding(10.dp)
        ) {
            Text(
                text = item.carNumber,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.8f)
            )
        }


        Box(
            modifier = Modifier
                .background(
                    color = BookingListItemGray
                )
                .padding(10.dp)
        ) {
            Text(
                text = if (item.booking) "Бронь" else "${item.price} ₽",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewBookingList() {
    BookingList()
}