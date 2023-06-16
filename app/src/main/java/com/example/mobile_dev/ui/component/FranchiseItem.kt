package com.example.mobile_dev.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_dev.R
import com.example.mobile_dev.ui.theme.LightBlue
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun FranchiseItem(
    image: String,
    title: String,
    price: String,
    totalShop: Int?,
    category: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .width(361.dp)
            .height(122.dp)
            .fillMaxWidth()
            .border(2.dp, LightBlue, RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            alignment = Alignment.Center,
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
        Column(
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
        ) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = "Start From",
                        fontWeight = FontWeight.Medium,
                        fontSize = 8.sp,
                    )
                    Text(
                        text = stringResource(R.string.idr) + " " + price,
                        fontWeight = FontWeight.Bold,
                        color = LightBlue,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.total_shop) + " " + totalShop + " " + stringResource(
                            R.string.shop
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                var photo: Int = R.drawable.fnb_icon
                when (category){
                    "1" -> {
                        photo = R.drawable.fnb_icon
                    }
                    "2" -> {
                        photo = R.drawable.education_icon
                    }
                    "3" -> {
                        photo = R.drawable.retail_icon
                    }
                    else -> {}
                }
                Image(
                    painter = painterResource(photo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .align(Alignment.Bottom)
                        .weight(1f, false)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FranchiseItemPreview() {
    MobiledevTheme {
        FranchiseItem(
            "https://storage.googleapis.com/bizzit-387412.appspot.com/logo-Janji%20Jiwa_1686755391721",
            "Mixue Ice Cream & Teaa",
            "3,5 Mil",
            1800,
            "1",
        )
    }
}