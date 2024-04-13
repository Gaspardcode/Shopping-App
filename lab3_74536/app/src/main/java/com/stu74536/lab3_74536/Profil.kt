package com.stu74536.lab3_74536

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Profile(navController: NavController){
    Column {
        ProfileHeader()
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(1) {
                SignOut(navController)
                Spacer(Modifier.height(10.dp))
                DeleteAccount(navController)
            }
        }
    }
}
@Composable
fun ProfileHeader() {
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
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black)
            Text(text = "Profile",
                modifier = Modifier.padding(top = 24.dp),
                fontWeight = FontWeight.Bold,
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
private fun SignOut(navController: NavController) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor("Sign out", Icons.AutoMirrored.Filled.Logout, "") {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(text = "Log out") },
            text = { Text(text = "You are about to logout") },
            dismissButton = { Button (
                onClick = { showWarningDialog = false }
            )
            { Text(text = "Cancel") } },
            confirmButton = {
                Button (
                    onClick = {
                        Firebase.auth.signOut()
                        showWarningDialog = false
                        navController.navigate(Routes.Login.route) }
                )
                { Text(text = "Confirm") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
@Composable
private fun DeleteAccount(navController: NavController) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor("Delete Account", Icons.Default.Delete, "") {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(text = "Delete") },
            text = { Text(text = "You are about to delete your account") },
            dismissButton = { Button (
                onClick = { showWarningDialog = false }
            )
            { Text(text = "Cancel") } },
            confirmButton = {
                Button (
                    onClick = {
                        Firebase.auth.currentUser!!.delete()
                        Firebase.auth.signOut()
                        showWarningDialog = false
                        navController.navigate(Routes.Register.route) }
                )
                { Text(text = "Confirm") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
fun RegularCardEditor(
    title: String,
    icon: ImageVector,
    content: String,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, White)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditor(
    title: String,
    icon: ImageVector,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color
) {
    Card(
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = highlightColor)
            }
            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }
            Icon(imageVector = icon, contentDescription ="Icon", tint = highlightColor)
        }
    }
}