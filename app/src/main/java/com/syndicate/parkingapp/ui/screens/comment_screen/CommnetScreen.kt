package com.syndicate.parkingapp.ui.screens.comment_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.data.model.CommentState
import com.syndicate.parkingapp.ui.screens.comment_screen.components.CommentItem
import com.syndicate.parkingapp.ui.screens.comment_screen.components.StarButton
import com.syndicate.parkingapp.ui.theme.BookingListItemGray
import com.syndicate.parkingapp.ui.theme.CancelColor
import com.syndicate.parkingapp.ui.theme.GreenButton
import com.syndicate.parkingapp.ui.theme.TextFieldContainer
import com.syndicate.parkingapp.ui.theme.TextHintColor
import com.syndicate.parkingapp.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    commentState: CommentState = CommentState(),
    navigateToBack: () -> Unit = { },
    getParkingPlaces: () -> Unit = { },
    updateComment: () -> Unit = { },
    sendComment: (String, Int) -> Unit = { _, _ -> },
    removeComment: () -> Unit = { }
) {

    LaunchedEffect(key1 = commentState) {
        updateComment()
    }

    var stateScreen by remember {
        mutableIntStateOf(0)
    }

    var ratingState by remember {
        mutableIntStateOf(commentState.parkingItem.rating)
    }
    var text by remember {
        mutableStateOf("")
    }

    BackHandler {
        navigateToBack()
        getParkingPlaces()
    }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp
                )
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
                        text = commentState.parkingItem.address,
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
                        repeat(commentState.parkingItem.rating) {
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

                        repeat(5 - commentState.parkingItem.rating) {
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
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(13.dp)
            )

            AnimatedVisibility(
                visible = stateScreen == 0,
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(5.dp)
                                )
                                .background(
                                    color = BookingListItemGray
                                )
                                .padding(
                                    vertical = 20.dp,
                                    horizontal = 40.dp
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    getParkingPlaces()
                                    navigateToBack()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Назад",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(
                                    start = 20.dp
                                )
                                .clip(
                                    RoundedCornerShape(5.dp)
                                )
                                .background(
                                    color = if (commentState.hasOwnComment) CancelColor else GreenButton
                                )
                                .padding(
                                    vertical = 20.dp
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    if (commentState.hasOwnComment) removeComment() else stateScreen =
                                        1
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (commentState.hasOwnComment) "Удалить отзыв" else "Оставить отзыв",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                    )

                    Text(
                        text = "Отзывы",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(18.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        commentState.listComments.forEach { commentObject ->
                            item {
                                CommentItem(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    item = commentObject
                                )

                                Spacer(
                                    modifier = Modifier
                                        .height(25.dp)
                                )
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = stateScreen == 1
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(5.dp)
                            )
                            .background(
                                color = CancelColor
                            )
                            .padding(
                                vertical = 20.dp
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                stateScreen = 0
                                text = ""
                                ratingState = 0
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Отмена",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                    )

                    Text(
                        text = "Напишите отзыв",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = text,
                        onValueChange = {
                            if (text.length < 300)
                                text = it
                        },
                        placeholder = {
                            Text(
                                text = "Комментарий...",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextHintColor
                            )
                        },
                        minLines = 3,
                        maxLines = 5,
                        singleLine = false,
                        shape = RoundedCornerShape(5.dp),
                        textStyle = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black.copy(alpha = 0.8f)
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            errorBorderColor = Color.Red,
                            cursorColor = GreenButton,
                            errorCursorColor = Color.Red,
                            selectionColors = TextSelectionColors(
                                handleColor = GreenButton,
                                backgroundColor = GreenButton.copy(alpha = 0.4f)
                            ),
                            containerColor = TextFieldContainer,
                            errorContainerColor = TextFieldContainer
                        )
                    )

                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ваша оценка",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black.copy(alpha = 0.8f)
                        )

                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                        )

                        Row {
                            repeat(5) { number ->

                                StarButton(
                                    active = when (number) {
                                        0 -> ratingState > 0
                                        1 -> ratingState > 1
                                        2 -> ratingState > 2
                                        3 -> ratingState > 3
                                        4 -> ratingState > 4
                                        else -> false
                                    },
                                    onClick = {
                                        when (number) {
                                            0 -> ratingState = 1
                                            1 -> ratingState = 2
                                            2 -> ratingState = 3
                                            3 -> ratingState = 4
                                            4 -> ratingState = 5
                                            else -> {}
                                        }
                                    }
                                )

                                Spacer(
                                    modifier = Modifier
                                        .width(6.dp)
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(5.dp)
                            )
                            .background(
                                color = GreenButton
                            )
                            .padding(
                                vertical = 20.dp
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                stateScreen = 0
                                sendComment(text, ratingState)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Оставить отзыв",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewCommentScreen() {
    CommentScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    )
}