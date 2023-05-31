package com.example.mobile_dev.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_dev.R
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun CamButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.width(120.dp)) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.lightblue)),
            modifier = modifier
                .width(120.dp)
                .height(40.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.AddAPhoto,
                modifier = modifier.size(32.dp),
                contentDescription = null,
                tint = colorResource(R.color.white),
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.worksans_bold, FontWeight.Bold)),
                color = colorResource(R.color.white),
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
fun CamButtonPreview() {
    MobiledevTheme() {
        ButtonApp(
            title = "Add Picture",
            onClick = {}
        )
    }
}