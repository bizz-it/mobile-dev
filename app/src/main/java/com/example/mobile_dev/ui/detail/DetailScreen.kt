package com.example.mobile_dev.ui.detail

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.example.mobile_dev.R
import com.example.mobile_dev.data.Result
import com.example.mobile_dev.databinding.ActivityDetailBinding
import com.example.mobile_dev.ui.agreement.ProgressOne
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun DetailScreen(
    franchiseId: String,
    context: Context = LocalContext.current,
        viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(context)
    ),
){
    val con = LocalLifecycleOwner.current
    AndroidViewBinding(ActivityDetailBinding::inflate) {
        this.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true, stringResource(R.string.detail), onClick = {}
                )
            }
        }
        this.composeButton.setContent {
            MobiledevTheme {
                ButtonApp(
                    stringResource(R.string.agreement),
                    onClick = {
                        val intent = Intent(context, ProgressOne::class.java)
                        intent.putExtra("id", franchiseId)
                        context.startActivity(intent)
                    }
                )
            }
        }
        viewModel.detailFranchise(franchiseId).observe(con) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading-> {
                    }
                    is Result.Success -> {
                        val listDetail = result.data?.data
                        Glide.with(context)
                            .load(listDetail?.get(0)?.foto)
                            .into(this.logo)
                        this.brand.text = listDetail?.get(0)?.nama
                        this.total.text = listDetail?.get(0)?.totalGerai.toString()
                        this.brandBig.text = context.getString(R.string.history, listDetail?.get(0)?.nama)
                        this.detailBrand.text = listDetail?.get(0)?.deskripsi
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }
}

