package com.stu74536.lab3_74536

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

data class Name (
    val firstname: String,
    val lastname: String,
)
data class Address (
    val number: String,
    val street: String,
    val city: String,
    val zipcode: String,
    val geolocation: Location,
)
data class Location (
    val lat: String,
    val long: String,
)
data class User (
    val username: String,
    val password: String,
    val name: Name,
    val address: Address,
    val phone: String,
)
data class Product (
    val id: String = "",
    val title: String = "",
    var price: Double = 0.0,
    var stock: Long = 0,
    val description: String = "",
    val image: String = ""
)
data class Category (
    val id: String,
    val title: String,
    val image:String,
)

data class ShoppingCart (
    var totalPrice: Double = 0.0,
    var shopIts:List<ShoppingItem> = emptyList(),
    var butEnabled:Boolean = false,
)
data class ShoppingItem (
    var quantity:Int = 0,
    var product:Product = Product(),
)
fun Prod2ShopIt(prod:Product, qty:Int) :ShoppingItem{
    return ShoppingItem(qty,prod)
}
fun addToCartInApp(sc:ShoppingCart, shopIt:ShoppingItem) {
    sc.totalPrice += (shopIt.product.price * shopIt.quantity.toDouble())
    sc.shopIts += shopIt
}
fun remFCart(sc:ShoppingCart, shopIt:ShoppingItem) {
    sc.totalPrice -= (shopIt.product.price * shopIt.quantity.toDouble())
    sc.shopIts = sc.shopIts.filter { it.product.id != shopIt.product.id  }
}
fun fetchCategories(onResult: (List<Category>) -> Unit) {
    Firebase.firestore.collection("categories")
        .get()
        .addOnSuccessListener { documents ->
            val categories = documents.map { document ->
                Category(document.id, document.getString("title") ?: "", document.getString("image") ?: "")
            }
            onResult(categories)
        }
        .addOnFailureListener { exception ->
            Log.d("Firestore", "Error getting documents: ", exception)
        }
}
suspend fun fetchPbyCat(category: String?): List<Product> {
    val products = mutableListOf<Product>()
    try {
        val querySnapshot =
            Firebase.firestore.collection("categories/$category/products").get().await()
        for (document in querySnapshot.documents) {
            val product = Product(
                document.id,
                document.getString("title") ?: "",
                document.getDouble("price") ?: 0.0,
                document.getLong("stock") ?: 0,
                document.getString("description") ?: "",
                document.getString("image") ?: ""
            )
            products.add(product)
        }
    } catch (e: Exception) {
        Log.d("Firestore", "Error getting documents: $e")
    }
    return products
}
suspend fun fetchProd(catId: String,prodId: String): Product {
    return try {
        val documentSnapshot = Firebase.firestore.collection("categories/$catId/products/").document(prodId).get().await()
        Log.d("Firestore", "categories/$catId/products/$catId")
        val data = documentSnapshot.data
        if (data != null) {
            val id = data["id"] as? String ?: ""
            val title = data["title"] as? String ?: ""
            val description = data["description"] as? String ?: ""
            val image = data["image"] as? String ?: ""
            val price = (data["price"] as? Double) ?: 0.0
            val stock = (data["stock"] as? Long) ?: 0
            Log.e("Firestore", "$id|$price|$stock|$image")
            Product(id, title, price, stock, description, image)

        } else {
            Product("", "Product Not Found", 0.0,0,"","")
        }
    } catch (e: Exception) {
        Log.d("Firestore", "Error getting document: $e")
        Product("", "Error Fetching Product", 0.0,0,"","")
    }
}
suspend fun addToFireStore(userId: String, shoppingItem: ShoppingItem) {
    val db = Firebase.firestore
    val cartRef = db.collection("users").document(userId)
    val shoppingItemRef = cartRef.collection("cart").document(shoppingItem.product.id)
    try {
        shoppingItemRef.set(shoppingItem).await()
    } catch (e: Exception) {
        println("Error adding shopping item to cart: $e")
    }
}
fun rmFromFireStore(userId: String, productId: String) {
    val db = Firebase.firestore
    val cartRef = db.collection("users").document(userId).collection("cart").document(productId)
    cartRef.delete()
}
fun GetCart(userId: String, callback: (ShoppingCart) -> Unit) {
    val shopCart = ShoppingCart(0.0,emptyList(),false)
    Firebase.firestore
        .collection("users")
        .document(userId)
        .collection("cart")
        .get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val shopIt = document.toObject<ShoppingItem>()
                if( shopIt != null){
                    addToCartInApp(shopCart,shopIt)
                }
            }
            callback(shopCart)
        }
        .addOnFailureListener { e ->
            Log.d("Firestore", "Error getting documents: $e")
            // Handle error
        }
}
// ===============================================================