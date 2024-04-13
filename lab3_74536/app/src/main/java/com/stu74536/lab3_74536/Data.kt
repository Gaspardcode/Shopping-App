package com.stu74536.lab3_74536

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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
    val id: String,
    val title: String,
    val price: Double,
    var stock: Long,
    val description:String,
    val image: String,
)
data class Category (
    val id: String,
    val title: String,
    val image:String,
)

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
fun addToCart(userId: String, productId: String, quantity: Int, price: Double) {
    val db = Firebase.firestore
    val cartRef = db.collection("users").document(userId).collection("cart").document(productId)

    cartRef.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val existingQuantity = documentSnapshot.getLong("quantity") ?: 0
            val newQuantity = existingQuantity + quantity

            cartRef.update("quantity", newQuantity)
        } else {
            val newCartItem = hashMapOf(
                "quantity" to quantity,
                "price" to price
            )
            cartRef.set(newCartItem)
        }
    }

    val data = db.collection("users").document(userId).collection("cart").document("data")

    cartRef.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val existingQuantity = documentSnapshot.getDouble("total") ?: 0.0
            val newQuantity = existingQuantity + quantity
            cartRef.update("total", newQuantity)
        } else {
            val newCartItem = hashMapOf(
                "quantity" to quantity,
                "price" to price
            )
            cartRef.set(newCartItem)
        }
    }
}

// Function to update the quantity of a product in the cart
fun updateCartItemQuantity(userId: String, productId: String, newQuantity: Int, price: Double) {
    val db = Firebase.firestore
    val cartRef = db.collection("users").document(userId).collection("cart").document(productId)

    cartRef.update("quantity", newQuantity, "price", (newQuantity * price))
}

// Function to remove a product from the cart
fun removeFromCart(userId: String, productId: String) {
    val db = Firebase.firestore
    val cartRef = db.collection("users").document(userId).collection("cart").document(productId)

    cartRef.delete()
}
// ===============================================================