package com.example.mobile_dev.ui.screen.catalog

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_dev.R
import com.example.mobile_dev.ui.component.CatalogItem
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CatalogScreen() {
    Scaffold(
        topBar = {
            TopBar(
                false, stringResource(R.string.menu_history), onClick = {}
            )
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)

        ) {
            CatalogItem(
                title = "Mixue Ice Cream & Tea",
                state = stringResource(id = R.string.pending),
            )
            Spacer(modifier = Modifier.padding(16.dp))
            CatalogItem(
                title = "Donburi Ichiya",
                state = stringResource(id = R.string.process)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            CatalogItem(
                title = "Sabana",
                state = stringResource(id = R.string.reject)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CatalogScreenPreview() {
    MobiledevTheme {
        CatalogScreen()
    }
}