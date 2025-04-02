package fr.uge.myfittracker.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import fr.uge.myfittracker.R

sealed class BottomNavItem(
    val navRoute: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    data object Home : BottomNavItem("home", R.string.nav_home, R.drawable.ic_home)
    data object Train : BottomNavItem("train", R.string.nav_training, R.drawable.ic_training)
    //data object Settings : BottomNavItem("settings", R.string.nav_settings, R.drawable.ic_settings)
}
