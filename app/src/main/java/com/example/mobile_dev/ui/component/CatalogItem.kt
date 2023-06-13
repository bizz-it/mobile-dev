package com.example.mobile_dev.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_dev.R
import com.example.mobile_dev.ui.theme.DarkBlue
import com.example.mobile_dev.ui.theme.LightBlue
import com.example.mobile_dev.ui.theme.MobiledevTheme
import com.example.mobile_dev.ui.theme.Yellow

@Composable
fun CatalogItem(
    title: String,
    state: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .width(361.dp)
            .height(150.dp)
            .fillMaxWidth()
            .border(2.dp, LightBlue, RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var color: Color = LightBlue
        var icon: ImageVector = Icons.Outlined.Info
        var textInfo: String = stringResource(id = R.string.info_process)
        when (state) {
            "Process" -> {
                color = LightBlue
                icon = Icons.Outlined.Info
                textInfo = stringResource(id = R.string.info_pending)
            }
            "Reject" -> {
                color = Yellow
                icon = Icons.Default.WarningAmber
            }
            "Pending" -> {
                color = Color.Green
                icon = Icons.Outlined.Timelapse
            }
        }
        Column(
            modifier = Modifier.padding(14.dp, 6.dp, 14.dp, 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = modifier.width(270.dp),
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.1.sp,
                    color = DarkBlue,
                    fontFamily = FontFamily(Font(R.font.worksans_bold, FontWeight.Bold)),
                    fontSize = 20.sp,
                )
                Row(
                    modifier = modifier
                        .width(80.dp)
                        .height(20.dp)
                        .fillMaxWidth()
                        .background(color.copy(0.2f), RoundedCornerShape(12.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily(Font(R.font.worksans_regular)),
                        letterSpacing = 0.1.sp,
                        fontSize = 10.sp,
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.submitted),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.worksans_semibold)),
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row( modifier = modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(color.copy(0.2f), RoundedCornerShape(12.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = icon,
                    modifier = modifier.size(28.dp),
                    contentDescription = null,
                    tint = color,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = modifier.width(260.dp),
                    text = textInfo,
                    letterSpacing = 0.1.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.worksans_regular)),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogItemPreview() {
    MobiledevTheme {
        CatalogItem(
            "Mixue Ice Cream & Tea",
            "Pending",
        )
    }
}