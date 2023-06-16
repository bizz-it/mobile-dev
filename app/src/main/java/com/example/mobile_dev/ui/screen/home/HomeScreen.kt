package com.example.mobile_dev.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobile_dev.R
import com.example.mobile_dev.data.response.DataItem
import com.example.mobile_dev.model.FakeCategoryDataSource.dummyCategory
import com.example.mobile_dev.ui.component.CategoryItem
import com.example.mobile_dev.ui.component.FranchiseItem
import com.example.mobile_dev.ui.component.HomeSection
import com.example.mobile_dev.ui.component.TopBarHome

@Composable
fun HomeScreen(
    listFranchise: List<DataItem>,
    navigateToDetail: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopBarHome() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
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
                content = { MenuRow(listFranchise, navigateToDetail) }
            )
        }
    }
}

@Composable
fun LiveTrack() {
    Image(
        painter = painterResource(id = R.drawable.live_track),
        contentDescription = null,
        modifier = Modifier.padding(16.dp)
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
    listFranchise: List<DataItem>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Adaptive(359.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        modifier = modifier
    ) {
        items(listFranchise) { franchise ->
            FranchiseItem(
                franchise.foto.toString(),
                franchise.nama.toString(),
                franchise.franchisePackages?.get(0)?.price.toString(),
                franchise.totalGerai,
                franchise.franchiseCategory?.id.toString(),
                modifier = modifier.clickable {
                    navigateToDetail(franchise.id.toString())
                }
            )
        }
    }
}
