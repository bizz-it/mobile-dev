package com.example.mobile_dev

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_dev.ui.navigation.NavigationItem
import com.example.mobile_dev.ui.navigation.Screen
import com.example.mobile_dev.ui.screen.catalog.CatalogScreen
import com.example.mobile_dev.ui.screen.education.ClassScreen
import com.example.mobile_dev.ui.screen.home.HomeScreen
import com.example.mobile_dev.ui.screen.profile.ProfileScreen
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun BizzitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailFranchise.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { franchiseId ->
                        navController.navigate(Screen.DetailFranchise.createRoute(franchiseId))
                    }
                )
            }
            composable(Screen.Catalog.route) {
                CatalogScreen()
            }
            composable(Screen.Camera.route) {
                // Camera
            }
            composable(Screen.Class.route) {
                ClassScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_history),
                icon = Icons.Default.History,
                screen = Screen.Catalog
            ),
            NavigationItem(
                title = stringResource(R.string.menu_camera),
                icon = Icons.Default.Camera,
                screen = Screen.Camera
            ),
            NavigationItem(
                title = stringResource(R.string.menu_class),
                icon = Icons.Default.Class,
                screen = Screen.Class
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            item.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    unselectedContentColor = LightGray,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BizzitAppPreview() {
    MobiledevTheme {
        BizzitApp()
    }
}