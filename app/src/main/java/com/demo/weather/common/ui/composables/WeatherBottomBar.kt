package com.demo.weather.common.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.demo.weather.R
import com.demo.weather.history.ui.HistoryScreenRoute
import com.demo.weather.map.ui.MapScreenRoute
import com.demo.weather.weather.ui.WeatherScreenRoute

@Composable
fun WeatherBottomBar(navController: NavController, selectedItem: Any, onItemSelected: (Any) -> Unit) {
    val items = listOf(
        WeatherNavItem(WeatherScreenRoute, R.string.home, R.drawable.home_24),
        WeatherNavItem(HistoryScreenRoute, R.string.recent, R.drawable.recent_24),
        WeatherNavItem(MapScreenRoute, R.string.map, R.drawable.map_24)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = null) },
                label = { Text(stringResource(item.title)) },
                selected = selectedItem == item.route,
                onClick = {
                    onItemSelected(item.route)
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class WeatherNavItem(val route: Any, val title: Int, val icon: Int)