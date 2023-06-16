package com.example.mobile_dev.ui.screen.education

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityClassBinding
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun ClassScreen(){
    AndroidViewBinding(ActivityClassBinding::inflate){
        this.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true, stringResource(R.string.menu_class), onClick = {}
                )
            }
        }
    }
}