package com.syndicate.parkingapp.ui.screens.comment_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.syndicate.parkingapp.data.model.CommentObject
import com.syndicate.parkingapp.ui.theme.CommentNameColor

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    item: CommentObject = CommentObject()
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = CommentNameColor
                    )
                    .padding(
                        vertical = 8.dp,
                        horizontal = 20.dp
                    )
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black.copy(alpha = 0.8f)
                )
            }

            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(item.rating) {
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

                repeat(5 - item.rating) {
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

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = item.comment,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black.copy(alpha = 0.8f)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewCommentItem() {
    CommentItem()
}