package com.stu74536.lab3_74536

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.stu74536.lab3_74536.ui.theme.roboto



@Composable
fun ShoppingCartHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // left side: label
        Column {
            Text(text = "Your",
                modifier = Modifier.padding(top = 24.dp),
                fontWeight = Bold,
                fontSize = 18.sp,
                color = Color.White)
            Text(text = "Shopping Cart",
                modifier = Modifier.padding(top = 24.dp),
                fontWeight = Bold,
                fontSize = 18.sp,
                color = Color.Green)
            }
        // right side: user icon
        AsyncImage(model = "https://thispersondoesnotexist.com/",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .clip(CircleShape))
    }
}
@Composable
fun CartList(navController: NavController,shoppingCart: ShoppingCart){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            items(shoppingCart.shopIts) { shopIt ->
                CartItem(navController, shoppingCart, shopIt)
            }
        }
        /*
            this.items(
                items = sc.shopIts,
                itemContent = {
                    CartItem(navController, sc = shoppingCart, shopIt = it)
                })
         */
        Bottom(shoppingCart)
    }
}
@Composable
fun Bottom(sc: ShoppingCart){
    Spacer(Modifier.height(20.dp))
    Column {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(Modifier.width(20.dp))
            Text(text = "Total : ${String.format("%.2f", sc.totalPrice)}€", fontWeight = Bold)
            Spacer(Modifier.width(100.dp))
            Text(text="${sc.shopIts.size}", color = Color.White,fontWeight = Bold)
            Text(text=" Orders",fontWeight = Bold)
            }
        }
        Spacer(Modifier.height(5.dp))
        BuyButton(sc)
}
@Composable
fun CartItem(navController: NavController,sc: ShoppingCart,shopIt: ShoppingItem){
    val user = FirebaseAuth.getInstance().currentUser!!
    var show by remember { mutableStateOf(true) }
    if (show){
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row {
                ShoppingImage(shopIt)
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center) {
                    Text(text = shopIt.product.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = roboto)
                    Spacer(Modifier.height(5.dp))
                    Text(text = shopIt.quantity.toString() + " x " + shopIt.product.price.toString() + "€",
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = roboto)
                    Spacer(Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Icon(imageVector = Icons.Default.Cancel,
                            contentDescription = null,
                            modifier = Modifier.run {
                                clickable {
                                //show = false
                                remFCart(sc, shopIt)
                                rmFromFireStore(user.uid, shopIt.product.id)
                                navController.navigate(Routes.Cart.route)
                            }
                            .size(50.dp, 50.dp)
                            },
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun BuyButton(sc:ShoppingCart){
    val buttonColors = if (sc.totalPrice > 0.0) {
        ButtonDefaults.buttonColors(Color.Gray)
    } else {
        ButtonDefaults.buttonColors(lBlack)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {},
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            colors = buttonColors,
            content = {
                Text(text = "Proceed to checkout", color = Color.White)
            })
    }
}
@Composable
fun Cart(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser!!
    var sc by remember {
        mutableStateOf(
            ShoppingCart()
        )
    }
    GetCart(user.uid) { shoppingCart ->
        sc = shoppingCart
    }
    Column (
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        ShoppingCartHeader()
        CartList(navController, shoppingCart = sc)
        /*
        Button(onClick = {
            runBlocking {
                launch(Dispatchers.IO) {
                    pushFashionItemsToFirestore(fashionItems, collectionName)
                    pushFashionItemsToFirestore(fashionItems, "categories/clothing/products")
                    pushFashionItemsToFirestore(electronicItems, collectionName)
                    pushFashionItemsToFirestore(fashionItems, "categories/electronics/products")
                    pushFashionItemsToFirestore(householdItems, collectionName)
                    pushFashionItemsToFirestore(fashionItems, "categories/household/products")
                }
            }
        }, content = {})
         */
    }
}

