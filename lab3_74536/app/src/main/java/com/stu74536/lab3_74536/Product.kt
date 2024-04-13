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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.stu74536.lab3_74536.ui.theme.roboto

@Composable
fun ProductsScreen(navController: NavController, products:List<Product>,catId:String){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        this.items(
            items = products,
            itemContent = {
                ProductListItem(product = it, navController, catId)
            })
    }
}

@Composable
fun ProductScreen(navController: NavController, product:Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
        Bottom(navController, product)
    }

}
@Composable
fun Bottom(navController: NavController, product:Product) {
    var qty by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = product.title,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = roboto)
        Spacer(Modifier.height(5.dp))
        Text(text = product.description,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = roboto)
        Spacer(Modifier.height(5.dp))
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = product.price.toString() + "€",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = roboto
            )
        }
        Spacer(Modifier.height(40.dp))
        Row(
                verticalAlignment = Alignment.CenterVertically
        ){
            Icon(imageVector = Icons.Default.Add,
                contentDescription = null,
                Modifier.clickable { if (product.stock > 0) {qty += 1; product.stock -= 1;} })
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = qty.toString())
            Spacer(modifier = Modifier.width(5.dp))
            Icon(imageVector = Icons.Default.Remove,
                contentDescription = null,
                Modifier.clickable { if (qty > 0) {qty -= 1; product.stock += 1;}})
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = {
                val user = FirebaseAuth.getInstance().currentUser
                if(user != null){
                    addToCart(user.uid,product.title,qty,product.price) ;
                }
                qty = 0;},
                content = {
                    Text(text = "BUY")
                })
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Stock left : " + product.stock.toString())
        }
    }
}