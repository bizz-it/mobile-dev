package com.example.mobile_dev.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Box(modifier = modifier.width(140.dp), contentAlignment = Alignment.Center) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.lightblue)),
            modifier = modifier
                .width(140.dp)
                .height(40.dp),
            contentPadding = PaddingValues(end = 80.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                modifier = modifier.size(16.dp),
                contentDescription = null,
                tint = colorResource(R.color.white),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.width(100.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.worksans_bold, FontWeight.Bold)),
                color = colorResource(R.color.white),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = modifier
                    .padding(start = 4.dp).fillMaxWidth(),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CamButtonPreview() {
    MobiledevTheme() {
        CamButton(
            title = "Add Picture",
            onClick = {}
        )
    }
}