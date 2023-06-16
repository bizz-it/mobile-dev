package com.example.mobile_dev.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun TopBar(
    isVisible: Boolean,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    var image: ImageVector? = Icons.Rounded.KeyboardArrowLeft
    if (!isVisible) { //Condition
        image = null
    }
    Column() {
        Box(modifier = modifier.fillMaxWidth()) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                modifier = modifier
                    .width(80.dp)
                    .height(64.dp)
            ) {
                if (image != null) {
                    Icon(
                        imageVector = image,
                        modifier = modifier.size(32.dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
            Row(modifier.height(64.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.worksans_bold, FontWeight.Bold)),
                    color = MaterialTheme.colorScheme.tertiary,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
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
fun TopBarPreview() {
    MobiledevTheme {
        TopBar(
            isVisible = false,
            title = "Agreement",
            onClick = {}
        )
    }
}