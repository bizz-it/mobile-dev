package com.example.mobile_dev.ui.agreement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_dev.R
import com.example.mobile_dev.databinding.ActivityProgressOneBinding
import com.example.mobile_dev.ui.component.ButtonApp
import com.example.mobile_dev.ui.component.CamButton
import com.example.mobile_dev.ui.component.PriceList
import com.example.mobile_dev.ui.component.RadioCostum
import com.example.mobile_dev.ui.component.TopBar
import com.example.mobile_dev.ui.detail.DetailActivity
import com.example.mobile_dev.ui.theme.MobiledevTheme

class ProgressOne : AppCompatActivity() {

    private lateinit var binding: ActivityProgressOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            MobiledevTheme {
                TopBar(
                    true,
                    getString(R.string.agreement),
                    onClick = {
                        val i = Intent(this@ProgressOne, DetailActivity::class.java)
                        startActivity(i)
                    }
                )
            }
        }

        binding.camBtn.setContent {
            MobiledevTheme {
                CamButton(
                    getString(R.string.add),
                    onClick = {
                        val i = Intent(this@ProgressOne, DetailActivity::class.java)
                        startActivity(i)
                    }
                )
            }
        }

        binding.nextBtn.setContent {
            MobiledevTheme {
                ButtonApp(
                    getString(R.string.next),
                    onClick = {
                        val i = Intent(this@ProgressOne, ProgressTwo::class.java)
                        startActivity(i)
                    }
                )
            }
        }

        binding.radiogroup.setContent {
            MobiledevTheme {
                val priceList = arrayListOf<PriceList>()
                priceList.add(
                    PriceList(
                        name = "Prince",
                        price = "250"
                    )
                )
                priceList.add(
                    PriceList(
                        name = "Lucky",
                        price = "500"
                    )
                )
                priceList.add(
                    PriceList(
                        name = "Frankie",
                        price = "300"
                    )
                )
                RadioCostum(
                    priceList
                )
            }
        }
    }
}