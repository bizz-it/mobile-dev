package com.example.mobile_dev

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_dev.ui.navigation.NavigationItem
import com.example.mobile_dev.ui.navigation.Screen
import com.example.mobile_dev.ui.screen.home.HomeScreen
import com.example.mobile_dev.ui.theme.MobiledevTheme

@Composable
fun BizzitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current

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
//                context.startActivity(Intent(context, HomeScreen::class.java))
//                HomeScreen(
//                    navigateToDetail = { rewardId ->
//                        navController.navigate(Screen.DetailReward.createRoute(rewardId))
//                    }
//                )
                HomeScreen()
            }
            composable(Screen.History.route) {
                // History
            }
            composable(Screen.Camera.route) {
                // Camera
            }
            composable(Screen.Class.route) {
                // Class
            }
            composable(Screen.Profile.route) {
                // Profile
            }
            composable(Screen.DetailFranchise.route) {
                // DetailFranchise
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
                screen = Screen.History
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
                screen = Screen.Profile,
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
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