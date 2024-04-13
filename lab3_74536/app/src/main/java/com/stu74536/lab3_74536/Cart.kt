package com.stu74536.lab3_74536

import android.icu.text.NumberFormat
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
import com.stu74536.lab3_74536.ui.theme.roboto


data class ShoppingCart (
    var totalPrice: Double,
    var shopIts:List<ShoppingItem>,
    var butEnabled:Boolean,
)
data class ShoppingItem (
    var quantity:Int,
    var product:Product,
)

fun sToF(input: String): Float {
    val nf = NumberFormat.getInstance()
    return nf.parse(input).toFloat()
}
public fun addToCartInApp(sc:ShoppingCart, shopIt:ShoppingItem) {
    sc.totalPrice += (shopIt.product.price * shopIt.quantity)
    sc.shopIts += shopIt
}
public fun remFCart(sc:ShoppingCart, shopIt:ShoppingItem) {
    sc.totalPrice -= (shopIt.product.price * shopIt.quantity)
    sc.shopIts -= shopIt
}
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
                .clip(RoundedCornerShape(corner = CornerSize(16.dp))))
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
            this.items(
                items = shoppingCart.shopIts,
                itemContent = {
                    CartItem(navController, sc = shoppingCart, shopIt = it)
                })
        }
        Bottom(shoppingCart)
    }

}
@Composable
fun Bottom(sc: ShoppingCart){
    Column {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(Modifier.width(10.dp))
            Text(text = "Total : " + sc.totalPrice.toString() + "€", fontWeight = Bold)
        }
        Spacer(Modifier.height(5.dp))
        BuyButton()
    }
}
@Composable
fun CartItem(navController: NavController,sc: ShoppingCart,shopIt: ShoppingItem){
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            ProductImage(shopIt.product)
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
                        modifier = Modifier
                            .clickable { remFCart(sc, shopIt) }
                            .size(50.dp, 50.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}
@Composable
fun BuyButton(){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {},
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(lBlack),
            content = {
                Text(text = "Proceed to checkout")
            })
    }
}
@Composable
fun Cart(navController: NavController) {
    val product by remember {
        mutableStateOf(
            Product(
                "",
                "Product Not Found",
                123.4,
                3,
                "lalalal",
                "https://thispersondoesnotexist.com/"
            )
        )
    }
    var sc by remember {
        mutableStateOf(
            ShoppingCart(1234.5, List<ShoppingItem>(2) {
                            ShoppingItem(12, product);
                            ShoppingItem(12, product)
                        }, true))
    }
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        ShoppingCartHeader()
        CartList(navController, shoppingCart = sc)
        Button(onClick = { /*TODO*/ }) {
            
        }
    }
}

