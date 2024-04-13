package com.stu74536.lab3_74536

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.stu74536.lab3_74536.ui.theme.roboto

@Composable
fun CategoryScreen(navController: NavController){
    CategoryList(navController)
}
@Composable
fun CategoryList(navController: NavController) {
    var categories by remember { mutableStateOf(listOf<Category>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        fetchCategories { fetchedCategories ->
            categories = fetchedCategories
            isLoading = false
        }
    }

    if (isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.Black)
                .fillMaxSize()
        ){
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.background(Color.Black)
                .fillMaxSize()
        ) {
            GridLayout(navController,items = categories)
        }
    }
}
@Composable
fun GridLayout(navController: NavController,items: List<Category>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(items) { _, item ->
            Card(
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .clickable { /* TODO GO TO PRODUCTS*/
                    navController.navigate(Routes.SecondScreen.route+ "/" + item.id)}
                    .padding(10.dp)

            ) {
                Column (
                    horizontalAlignment =  Alignment.CenterHorizontally,
                    modifier = Modifier
                            .background(Color.Gray)
                ){
                    CategoryImage(category = item)
                    Text(text = item.title,
                        fontFamily = roboto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White)
                }
            }
        }
    }
}
@Composable
fun CategoryImage(category:Category) {
    //val painter = rememberAsyncImagePainter(category.image)
    //CoilImageWithLoadingIndicator(painter)
    AsyncImage(
        model = category.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp)
            .clip(RoundedCornerShape(corner = CornerSize(40.dp)))
    )
}
/*
@Composable
private fun CoilImageWithLoadingIndicator(painter: AsyncImagePainter) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }
            is AsyncImagePainter.State.Error -> {
                // Handle error state if needed
            }
            else -> {
                // Display the image once it's loaded
                AsyncImage(
                    model = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(84.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                )
            }
        }
    }
}
 */
