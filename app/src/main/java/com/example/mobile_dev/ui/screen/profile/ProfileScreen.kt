package com.example.mobile_dev.ui.screen.profile

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProfileBinding
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun ProfileScreen(){
    AndroidViewBinding(ActivityProfileBinding::inflate){
        this.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    false, stringResource(R.string.menu_profile), onClick = {}
                )
            }
        }
//        this.
    }
}


