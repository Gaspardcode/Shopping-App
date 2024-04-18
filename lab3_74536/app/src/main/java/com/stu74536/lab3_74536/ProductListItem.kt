package com.stu74536.lab3_74536

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.stu74536.lab3_74536.ui.theme.roboto

@Composable
fun ProductImage(product: Product) {
    AsyncImage(
        model = product.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(14.dp)
            .size(120.dp)
            .clip(RoundedCornerShape(corner = CornerSize(24.dp)))
    )
}
@Composable
fun ShoppingImage(shopIt: ShoppingItem) {
    AsyncImage(
        model = shopIt.product.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(14.dp)
            .size(120.dp)
            .clip(RoundedCornerShape(corner = CornerSize(24.dp)))
    )
}

@Composable
fun ProductListItem(product: Product, navController: NavController, catId:String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(Routes.ThirdScreen.route + "/" + catId + "/" + product.id) },
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            ProductImage(product)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center) {
                Text(text = product.title, style = MaterialTheme.typography.titleLarge, fontFamily = roboto)
                Spacer(Modifier.height(5.dp))
                Text(text = product.price.toString() + "€", style = MaterialTheme.typography.titleMedium, fontFamily = roboto)
                Spacer(Modifier.height(5.dp))
                Text(text = product.stock.toString() + " left !", style = MaterialTheme.typography.titleMedium, fontFamily = roboto)
            }
        }
    }
}