package com.stu74536.lab3_74536

sealed class Routes(val route: String) {
    object FirstScreen : Routes("Home")
    object SecondScreen : Routes("Categories")
    object ThirdScreen : Routes("Product")
    object Cart : Routes("Cart")
    object Profile : Routes("UserProfile")
    object Login : Routes("Login")
    object Register : Routes("Register")
    object About : Routes("About")
}