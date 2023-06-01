package com.example.mobile_dev.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_dev.R
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun RadioCostum(listPackage: ArrayList<PriceList>, modifier: Modifier = Modifier) {

    var selectedItem by remember { mutableStateOf(listPackage[0].name) }

    Column(
        modifier = modifier
//            .verticalScroll(state = rememberScrollState())
            .selectableGroup().border(
                width = 1.dp,
                color = colorResource(R.color.lightblue),
                shape = RoundedCornerShape(percent = 4)
            ).padding(12.dp)
    ) {
        CompositionLocalProvider {
            listPackage.forEach { priceList ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedItem == priceList.name),
                            onClick = { selectedItem = priceList.name },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButtonStyle(selectedItem = selectedItem, priceList = priceList)
                }
            }
        }
    }
}

@Composable
private fun RadioButtonStyle(selectedItem: String, priceList: PriceList, modifier: Modifier = Modifier,) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .width(350.dp)
            .border(
                width = 1.dp,
                color =
                if (selectedItem == priceList.name)
                    colorResource(R.color.lightblue)
                else
                    colorResource(R.color.grey),
                shape = RoundedCornerShape(percent = 10)
            )
            .padding(start = 16.dp, end = 22.dp, top = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp, bottom = 2.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = priceList.name,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.worksans_semibold,
                                weight = FontWeight.Normal
                            )
                        )
                    )
                }
                Text(
                    text = "Rp ${priceList.price}",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.worksans_bold,
                            weight = FontWeight.Bold
                        )
                    ), modifier = modifier.offset(y = -6.dp)
                )
            }
        }
        Icon(
            modifier = modifier.size(32.dp),
            imageVector =
            if (selectedItem == priceList.name)
                Icons.Default.RadioButtonChecked
            else
                Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint =
            if (selectedItem == priceList.name)
                colorResource(R.color.lightblue)
            else
                colorResource(R.color.grey)
        )
    }
}

data class PriceList(val name: String, val price: String)

@Preview(showBackground = true)
@Composable
fun RadioCostumPreview() {
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