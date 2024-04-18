package com.stu74536.lab3_74536
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

val fashionItems = listOf(
    Product(
        id = "denim_jacket_01",
        title = "Denim Jacket",
        price = 59.99,
        stock = 50,
        description = "Classic denim jacket for a trendy look",
        image = "https://source.unsplash.com/featured/?denim+jacket"
    ),
    Product(
        id = "black_leather_boots_02",
        title = "Black Leather Boots",
        price = 79.99,
        stock = 30,
        description = "Stylish black leather boots for any occasion",
        image = "https://source.unsplash.com/featured/?black+leather+boots"
    ),
    Product(
        id = "striped_tshirt_03",
        title = "Striped T-Shirt",
        price = 24.99,
        stock = 100,
        description = "Casual striped t-shirt for a laid-back look",
        image = "https://source.unsplash.com/featured/?striped+tshirt"
    ),
    Product(
        id = "slim_fit_jeans_04",
        title = "Slim Fit Jeans",
        price = 39.99,
        stock = 70,
        description = "Comfortable slim fit jeans for everyday wear",
        image = "https://source.unsplash.com/featured/?slim+fit+jeans"
    ),
    Product(
        id = "sneakers_05",
        title = "Sneakers",
        price = 49.99,
        stock = 80,
        description = "Versatile sneakers for a sporty and casual look",
        image = "https://source.unsplash.com/featured/?sneakers"
    ),
    Product(
        id = "formal_dress_06",
        title = "Formal Dress",
        price = 129.99,
        stock = 20,
        description = "Elegant formal dress for special occasions",
        image = "https://source.unsplash.com/featured/?formal+dress"
    ),
    Product(
        id = "summer_hat_07",
        title = "Summer Hat",
        price = 19.99,
        stock = 60,
        description = "Stylish summer hat to keep you cool",
        image = "https://source.unsplash.com/featured/?summer+hat"
    ),
    Product(
        id = "leather_belt_08",
        title = "Leather Belt",
        price = 29.99,
        stock = 90,
        description = "Classic leather belt to complete your look",
        image = "https://source.unsplash.com/featured/?leather+belt"
    ),
    Product(
        id = "turtleneck_sweater_09",
        title = "Turtleneck Sweater",
        price = 49.99,
        stock = 40,
        description = "Warm turtleneck sweater for chilly days",
        image = "https://source.unsplash.com/featured/?turtleneck+sweater"
    ),
    Product(
        id = "pleated_skirt_10",
        title = "Pleated Skirt",
        price = 34.99,
        stock = 55,
        description = "Feminine pleated skirt for a stylish look",
        image = "https://source.unsplash.com/featured/?pleated+skirt"
    )
)
suspend fun pushFashionItemsToFirestore(fashionItems: List<Product>, collectionName: String) {
    val db = FirebaseFirestore.getInstance()
    try {
        for (item in fashionItems) {
            val itemId = item.id
            val productData = item.copy(id = itemId)
            db.collection(collectionName)
                .document(itemId)
                .set(productData)
                .await()
            println("Fashion item $itemId added to Firestore")
        }
        println("All fashion items added successfully to Firestore")
    } catch (e: Exception) {
        println("Error pushing fashion items to Firestore: $e")
    }
}
val electronicItems = listOf(
    Product(
        id = "laptop_01",
        title = "Laptop",
        price = 899.99,
        stock = 20,
        description = "Powerful laptop for work and entertainment",
        image = "https://source.unsplash.com/featured/?laptop"
    ),
    Product(
        id = "smartphone_02",
        title = "Smartphone",
        price = 699.99,
        stock = 50,
        description = "High-end smartphone with advanced features",
        image = "https://source.unsplash.com/featured/?smartphone"
    ),
    Product(
        id = "smart_watch_03",
        title = "Smart Watch",
        price = 199.99,
        stock = 30,
        description = "Feature-rich smart watch for fitness and notifications",
        image = "https://source.unsplash.com/featured/?smartwatch"
    ),
    Product(
        id = "headphones_04",
        title = "Wireless Headphones",
        price = 149.99,
        stock = 40,
        description = "Premium wireless headphones for immersive audio experience",
        image = "https://source.unsplash.com/featured/?headphones"
    ),
    Product(
        id = "desktop_computer_05",
        title = "Desktop Computer",
        price = 1299.99,
        stock = 15,
        description = "High-performance desktop computer for gaming and productivity",
        image = "https://source.unsplash.com/featured/?desktop"
    ),
    Product(
        id = "4k_television_06",
        title = "4K Television",
        price = 1499.99,
        stock = 10,
        description = "Immersive 4K television for stunning visual experience",
        image = "https://source.unsplash.com/featured/?tv"
    ),
    Product(
        id = "digital_camera_07",
        title = "Digital Camera",
        price = 799.99,
        stock = 25,
        description = "Professional-grade digital camera for capturing memories",
        image = "https://source.unsplash.com/featured/?camera"
    ),
    Product(
        id = "gaming_console_08",
        title = "Gaming Console",
        price = 399.99,
        stock = 30,
        description = "Next-gen gaming console for endless entertainment",
        image = "https://source.unsplash.com/featured/?gaming"
    ),
    Product(
        id = "wireless_router_09",
        title = "Wireless Router",
        price = 79.99,
        stock = 40,
        description = "High-speed wireless router for seamless internet connectivity",
        image = "https://source.unsplash.com/featured/?router"
    ),
    Product(
        id = "portable_charger_10",
        title = "Portable Charger",
        price = 39.99,
        stock = 50,
        description = "Compact portable charger for charging devices on the go",
        image = "https://source.unsplash.com/featured/?charger"
    ),
    Product(
        id = "ZenBook DF45",
        title = "ZenBook DF45",
        price = 1299.99,
        stock = 3,
        description = "Wonderful pc, valuable and resilient. Warrany for a lifetime.",
        image = "https://images.pexels.com/photos/2047905/pexels-photo-2047905.jpeg?auto=compress&cs=tinysrgb&w=600"
    ),
)
val householdItems = listOf(
    Product(
        id = "vacuum_cleaner_01",
        title = "Vacuum Cleaner",
        price = 149.99,
        stock = 30,
        description = "Powerful vacuum cleaner for effective cleaning",
        image = "https://source.unsplash.com/featured/?vacuum"
    ),
    Product(
        id = "coffee_maker_02",
        title = "Coffee Maker",
        price = 79.99,
        stock = 40,
        description = "Automatic coffee maker for brewing delicious coffee",
        image = "https://source.unsplash.com/featured/?coffee"
    ),
    Product(
        id = "toaster_03",
        title = "Toaster",
        price = 29.99,
        stock = 50,
        description = "Compact toaster for toasting bread slices",
        image = "https://source.unsplash.com/featured/?toaster"
    ),
    Product(
        id = "microwave_04",
        title = "Microwave Oven",
        price = 99.99,
        stock = 25,
        description = "Convenient microwave oven for quick cooking",
        image = "https://source.unsplash.com/featured/?microwave"
    ),
    Product(
        id = "air_purifier_05",
        title = "Air Purifier",
        price = 199.99,
        stock = 20,
        description = "Advanced air purifier for clean and fresh air",
        image = "https://source.unsplash.com/featured/?air-purifier"
    ),
    Product(
        id = "blender_06",
        title = "Blender",
        price = 49.99,
        stock = 35,
        description = "Versatile blender for making smoothies and soups",
        image = "https://source.unsplash.com/featured/?blender"
    ),
    Product(
        id = "dishwasher_07",
        title = "Dishwasher",
        price = 399.99,
        stock = 15,
        description = "Efficient dishwasher for sparkling clean dishes",
        image = "https://source.unsplash.com/featured/?dishwasher"
    ),
    Product(
        id = "kettle_08",
        title = "Electric Kettle",
        price = 34.99,
        stock = 45,
        description = "Fast-boiling electric kettle for hot beverages",
        image = "https://source.unsplash.com/featured/?kettle"
    ),
    Product(
        id = "iron_09",
        title = "Steam Iron",
        price = 59.99,
        stock = 30,
        description = "Powerful steam iron for wrinkle-free clothes",
        image = "https://source.unsplash.com/featured/?iron"
    ),
    Product(
        id = "food_processor_10",
        title = "Food Processor",
        price = 129.99,
        stock = 20,
        description = "Multi-functional food processor for easy food preparation",
        image = "https://source.unsplash.com/featured/?food-processor"
    )
)

val collectionName = "products"