package com.stu74536.lab3_74536

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


val LightBlack = Color(0xff111111)
val LightGray = Color.LightGray
val White = Color(0xffffffff)
val Black = Color(0x00000000)

@Composable
fun VerifyToken(auth: FirebaseAuth, navController: NavController) {
    val currentUser = auth.currentUser
    var showWarningDialog by remember { mutableStateOf(false) }

    currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val idToken = task.result?.token
            // Here you can send the token to your server for verification
            // For demonstration, we'll just update the UI with the verification result

        }
        else {
            Log.e("TokenVerification", "Error verifying token: ${task.exception?.message}")
            showWarningDialog = true
        }
    }
    if (showWarningDialog) {
        Dialog(navController)
    }
}
@Composable
fun Dialog(navController: NavController){
    AlertDialog(
        backgroundColor = lBlack,
        title = { Text(text = "Token Authentication", color = White) },
        text = { Text(text = "Your token has expired. You are about to be logged out", color = White) },
        confirmButton = {
            Button (
                onClick = {
                    Firebase.auth.signOut()
                    navController.navigate(Routes.Login.route) }
            )
            { Text(text = "Continue", color = White) }
        },
        dismissButton = {  Firebase.auth.signOut()
            navController.navigate(Routes.Login.route)
                        },
        onDismissRequest = {  Firebase.auth.signOut()
            navController.navigate(Routes.Login.route)
        }
    )
}
@Composable
fun Login(navController: NavController,modifier: Modifier = Modifier)
{
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(LightBlack),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            UpperPartDark()
            CoreRowDark(navController, auth = auth)
            SignInDark(navController)
        }
    }
    else{
        navController.navigate(Routes.FirstScreen.route)
    }
}
@Composable
fun Register(navController: NavController,modifier: Modifier = Modifier,)
{
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .background(LightBlack),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )   {
        UpperPartSignUpDark()
        CoreRowSignUpDark(navController = navController,auth = FirebaseAuth.getInstance())
        SignUpAlternativeDark(navController)
    }
}

@Composable
fun CoreRowDark(navController: NavController,modifier: Modifier = Modifier, auth:FirebaseAuth)
{
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorDisplay by remember { mutableStateOf(false) }
    var logging by remember { mutableStateOf("Login") }
    var err by remember { mutableStateOf("") }

    val ic = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val tic = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = email,
            onValueChange = { foo -> email = foo },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                disabledContainerColor = Black,
            ),
            leadingIcon = ic,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
            )
        Spacer(modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(1f))
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = password,
            onValueChange = { foo -> password = foo },
            label = { Text(text = "Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                disabledContainerColor = Black,
            ),
            trailingIcon = tic,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
        )
        Row(
            horizontalArrangement = Arrangement.End,
        ){
            Spacer(modifier = Modifier .fillMaxWidth(0.5f))
            Text(text = "Forgot Password?", color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /*TODO LOGIN */
                if (email.text != "" && password.text != "") {
                    logging = "Logging In ..."
                    auth.signInWithEmailAndPassword(email.text, password.text)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("EmailPassword", "signInWithEmail:success")
                                errorDisplay = false
                                val user = auth.currentUser
                                navController.navigate(Routes.FirstScreen.route)
                            } else {
                                errorDisplay = true
                                logging = "Login"
                                Log.w("EmailPassword", "signInWithEmail:failure", task.exception)
                            }
                        }
                } else {
                    errorDisplay = true
                    }},
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = logging, color = White, fontWeight = FontWeight.Bold)
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth(1f)
        )
        Text(text = if (errorDisplay) {"Incorrect credentials. Consider registering or re-try." } else {""}, color = Color.Red, fontSize = 15.sp)
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth(1f)
        )

    }
}
@Composable
fun UpperPartDark(modifier: Modifier = Modifier)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val imageModifier = Modifier
            .size(120.dp)
        Image(
            painter = painterResource(id = R.drawable.padlock_open),
            contentDescription = "A padlock image ",
            modifier = imageModifier
        )
        Spacer(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(1f))
        Text(text = "Welcome back you've been missed!", color = Color.DarkGray)
        Spacer(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(1f))
    }
}
@Composable
fun SignInDark(navController:NavController, modifier: Modifier = Modifier)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth(1f))
        Row (
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Not a member ?",
                color = Color.DarkGray)
            Text(text = AnnotatedString("Register Now"),
                color = LightGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.
                clickable{ navController.navigate(Routes.Register.route) },
            )
        }
    }
}
@Composable
fun UpperPartSignUpDark(modifier: Modifier = Modifier)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageModifier = Modifier
            .size(120.dp)
        Image(
            painter = painterResource(id = R.drawable.padlock_open),
            contentDescription = "A padlock image ",
            modifier = imageModifier
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Let's create an account for you", color = LightGray)
        Spacer(modifier = Modifier.height(20.dp))
    }
}
@Composable
fun CoreRowSignUpDark(modifier: Modifier = Modifier,navController: NavController, auth:FirebaseAuth)
{
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confpassword by remember { mutableStateOf(TextFieldValue("")) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorDisplay by remember { mutableStateOf(false) }
    var logs by remember { mutableStateOf("") }
    var signingIn by remember { mutableStateOf("Sign Up") }
    val ic = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val tic = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { foo -> email = foo },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                disabledContainerColor = Black,
            ),
            leadingIcon = ic,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = password,
            onValueChange = { foo -> password = foo },
            label = { Text(text = "Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                disabledContainerColor = Black,
            ),
            trailingIcon = tic,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = confpassword,
            onValueChange = { foo -> confpassword = foo },
            label = { Text(text = "Confirm Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                disabledContainerColor = Black,
            ),
            trailingIcon = tic,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /*TODO SIGNUP*/
                      if (password == confpassword) {
                          if (password.text.length >= 8){
                          signingIn = "Signing In ..."
                          auth.createUserWithEmailAndPassword(email.text, password.text)
                                  .addOnCompleteListener { task ->
                                      if (task.isSuccessful) {
                                          errorDisplay = false;
                                          Log.d("EmailPassword", "createUserWithEmail:success")
                                          val user = auth.currentUser
                                          val db = Firebase.firestore
                                          if (user != null) {
                                              val emptyMap = hashMapOf<String, Any>()
                                              db.collection("users")
                                                  .document(user.uid).set(emptyMap)
                                              db.collection("users").document(user.uid)
                                                  .collection("cart")
                                                  .document("dummy").set(emptyMap)
                                              db.collection("users").document(user.uid)
                                                  .collection("cart")
                                                  .document("dummy")
                                                  .delete()
                                          }
                                          navController.navigate(Routes.FirstScreen.route)
                                      } else {
                                          // If sign in fails, display a message to the user.
                                          Log.w(
                                              "EmailPassword",
                                              "createUserWithEmail:failure",
                                              task.exception
                                          )
                                          logs =
                                              "You are already registered. Please go to the login page."
                                          errorDisplay = true
                                      }
                                  }
                              }
                          else {
                              errorDisplay = true
                              logs = "Your password must be at least 8 characters long"
                          }
                      }
                      else {
                          logs = "The passwords do not match"
                          errorDisplay = true
                      }
                      },
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.2f),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = signingIn, color = White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth(1f))
        Text(text = if (errorDisplay) {logs} else {""}, color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
fun SignUpAlternativeDark(navController: NavController,modifier: Modifier = Modifier)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth(1f))
        Row (
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Already a member ?",
                color = LightGray)
            Text(text = "Login Now",
                color = White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.
                clickable{ navController.navigate(Routes.Login.route) },)
        }
    }
}
