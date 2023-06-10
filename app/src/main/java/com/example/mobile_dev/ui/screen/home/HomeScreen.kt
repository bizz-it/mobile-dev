package com.example.mobile_dev.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobile_dev.R
import com.example.mobile_dev.model.FakeFranchiseDataSource.dummyFranchises
import com.example.mobile_dev.model.Franchise
import com.example.mobile_dev.model.dummyCategory
import com.example.mobile_dev.ui.component.CategoryItem
import com.example.mobile_dev.ui.component.FranchiseItem
import com.example.mobile_dev.ui.component.HomeSection
import com.example.mobile_dev.ui.component.TopBarHome

@Composable
fun HomeScreen(
) {
    Scaffold(
        topBar = { TopBarHome() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            HomeSection(
                title = stringResource(R.string.section_live_track),
                content = { LiveTrack() }
            )
            HomeSection(
                title = stringResource(R.string.section_category),
                content = { CategoryRow() }
            )
            HomeSection(
                title = stringResource(R.string.section_trending_franchises),
                content = { MenuRow(dummyFranchises) }
            )
        }
    }
}

@Composable
fun LiveTrack() {
    Image(
        painter = painterResource(id = R.drawable.live_track),
        contentDescription = null
    )
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(dummyCategory, key = { it.textCategory }) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun MenuRow(
    listFranchise: List<Franchise>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        items(listFranchise, key = { it.id }) { franchise ->
            FranchiseItem(
                franchise.image,
                franchise.title,
                franchise.price,
                franchise.totalShop,
                franchise.category
            )
        }
    }
}
