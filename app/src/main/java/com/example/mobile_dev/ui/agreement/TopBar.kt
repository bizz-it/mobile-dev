package com.example.mobile_dev.ui.agreement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_dev.R
import com.example.mobile_dev.compose.theme.MobiledevTheme

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = modifier
                .width(80.dp)
                .height(64.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                modifier = modifier.size(32.dp),
                contentDescription = null,
                tint = colorResource(R.color.darkblue),
            )
        }
        Row(modifier.height(64.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.worksans_bold, FontWeight.Bold)),
                color = colorResource(R.color.darkblue),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = modifier
                    .height(28.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview() {
    MobiledevTheme() {
        TopBar(
            title = "Agreement",
            onClick = {}
        )
    }
}