package com.stu74536.lab3_74536


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var bottomVisible by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = { if (bottomVisible) {BottomNavigationBar(navController) }
                    else { Spacer(Modifier.fillMaxHeight(0f))} },
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = Routes.Login.route,
            modifier = Modifier
            .padding(inner),
        ) {
            composable(Routes.Login.route)
            {
                bottomVisible = false
                Login(navController)
            }
            composable(Routes.Register.route)
            {
                bottomVisible = false
                Register(navController)
            }
            composable(Routes.FirstScreen.route) {
                bottomVisible = true
                CategoryScreen(navController)
            }
            composable(Routes.SecondScreen.route + "/{categoryId}")
            { backStackEntry ->
                val catId = backStackEntry.arguments?.getString("categoryId")
                var products by remember { mutableStateOf(listOf<Product>()) }
                LaunchedEffect(catId) {
                    products = fetchPbyCat(catId)
                }
                ProductsScreen(navController, products = products, catId ?: "")
            }
            composable(Routes.ThirdScreen.route + "/{catId}/{productId}")
            { backStackEntry ->
                val catId = backStackEntry.arguments?.getString("catId")
                val prodId = backStackEntry.arguments?.getString("productId")
                var product by remember { mutableStateOf(Product("",
                    "Product Not Found",
                    0.0,
                    0,
                    "",
                    ""
                )) }
                LaunchedEffect(catId,prodId) {
                    product = fetchProd(catId ?: "",prodId ?: "")
                }
                ProductScreen(navController, product)
            }
            composable(Routes.Cart.route){
                VerifyToken(auth = FirebaseAuth.getInstance(), navController = navController)
                Cart(navController)
            }
            composable(Routes.Profile.route){
                VerifyToken(auth = FirebaseAuth.getInstance(), navController = navController)
                Profile(navController)
            }
        }
    }
}
