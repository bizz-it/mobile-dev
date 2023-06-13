package com.example.mobile_dev.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_dev.R
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun TopBarHome(
    modifier: Modifier = Modifier,
) {
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(16.dp, 10.dp)
                .fillMaxWidth()
        ) {
            SearchBar()
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = null,
                tint = colorResource(R.color.darkblue),
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(.25f),
                            Color.Transparent,
                        )
                    )
                )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TopBarHomePreview() {
    MobiledevTheme() {
        TopBarHome()
    }
}