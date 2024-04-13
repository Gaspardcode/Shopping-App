package com.stu74536.lab3_74536

import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

val lBlack = Color(0xFF5F5F5F)
sealed class BottomNavItem(var title:String, var icon:ImageVector, var screenRoute:String){
    data object Home: BottomNavItem("Products",Icons.Default.Home,Routes.FirstScreen.route)
    data object Cart: BottomNavItem("Cart",Icons.Default.ShoppingCart,Routes.Cart.route)
    data object Profile: BottomNavItem("Profile",Icons.Default.Person,Routes.Profile.route)
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Profile,
    )
    BottomNavigation(
        backgroundColor = lBlack,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Image( imageVector = item.icon, contentDescription = "") },
                label = { Text(text = item.title,
                    fontSize = 10.sp) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    if(currentRoute != item.screenRoute) {
                        navController.navigate(item.screenRoute)
                    }
                }
            )
        }
    }
}
