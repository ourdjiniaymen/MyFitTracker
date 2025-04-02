package fr.uge.myfittracker.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import fr.uge.myfittracker.ui.theme.darkGrey
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.ui.theme.secondary
import androidx.navigation.compose.currentBackStackEntryAsState
import fr.uge.myfittracker.ui.theme.darkerGrey


@Composable
fun BottomNavBar(navController: NavController, darkTheme: Boolean) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Train,
        //BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentItem = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = if (darkTheme) Color.Black else Color.White,
        contentColor = primary
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title.toString()
                    )
                },
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentItem == item.navRoute,
                onClick = {
                    navController.navigate(item.navRoute)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = if (darkTheme) primary else secondary,
                    unselectedIconColor = darkGrey,
                    selectedTextColor = if (darkTheme) primary else secondary,
                    indicatorColor = if (darkTheme) darkerGrey else Color.White
                )
            )
        }
    }
}
